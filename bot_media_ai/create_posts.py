from random import Random
import requests
from ollama import Client
from dotenv import load_dotenv
import os
import concurrent.futures

import threading
from time import sleep

URL = "http://localhost:8080"

def login(bot_number):
    """Login and get authentication token for a bot."""
    data = {
        "username": f"bot{bot_number}",
        "password": "123456"
    }
    response = requests.post(f"{URL}/auth/login", json=data)
    print("token : " + response.json()["token"])
    return response.json()["token"]

def generate_post_content():
    """Generate post content using Mistral model."""
    client = Client(host='http://localhost:11434')
    response = client.generate(
        model='llama3.2:latest',
        prompt="Generate a random post about technology with 1000 characters. Include current tech trends and innovations."
    )
    return response['response'].strip()

def create_post(token, content, imageName):
    """Create a post with given content and image."""
    headers = {"Authorization": f"Bearer {token}"}

    with open(f"postdownload/" + imageName, 'rb') as f:
        files = {
            'postImage': (imageName, f, 'image/jpeg')
        }
        data = {
            'postContent': content
        }
        print("image name :", imageName)
        response = requests.post(f"{URL}/post/upload", headers=headers, data=data, files=files)
        return response.json()

def get_random_tech_image():
    """Get a random technology-related image URL from Unsplash."""
    load_dotenv()
    # Use the saved image in create_post
   
    response = requests.get("https://api.unsplash.com/photos/random", params={
        "query": "technology",
        "orientation": "landscape",
        "client_id": os.getenv("UNSPLASH_API_KEY")
    })
    if response.status_code == 200:
        return response.json()["urls"]["regular"]
    return "https://cdn.pixabay.com/photo/2021/08/04/13/06/software-developer-6521720_640.jpg"  # Fallback URL

def main():
    # Login all bots

    # Login all bots
    tokens = [login(i) for i in range(1, 6)]
    thread_lock = threading.Lock()

    def create_single_post(i):
        token = tokens[i % 5]
        post_content = generate_post_content()
        image_url = get_random_tech_image()
        
        with thread_lock:
            os.makedirs("postdownload", exist_ok=True)
            imageName = f"image_{os.urandom(8).hex()}.jpg"
            
            # Download and save image
            image_response = requests.get(image_url)
            with open(f"postdownload/" + imageName, 'wb') as f:
                f.write(image_response.content)
            
            result = create_post(token, post_content, imageName)
            print(result)

    # Create posts using thread pool
    with concurrent.futures.ThreadPoolExecutor(max_workers=5) as executor:
        futures = [executor.submit(create_single_post, i) for i in range(15)]
        concurrent.futures.wait(futures)

if __name__ == "__main__":
    main()
