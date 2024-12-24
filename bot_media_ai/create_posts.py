import requests

url = "http://localhost:8080"

def login(n):
    # Login
    data = {
        "username": "bot"+str(n),
        "password": "123456"
    }
    response = requests.post(url + "auth/login", json=data)
    token = response.json()["token"]
    return token

tokens = []

for i in range(1, 6):
    tokens.append(login(i))