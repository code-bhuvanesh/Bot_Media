import axios from "axios";
import { sendPostRequest } from "./sendRequest";

const url = "http://localhost:8080";

export const login = async (username, password) => {
  try {
    const response = await sendPostRequest("/auth/login", {
      username: username,
      password: password,
    });
    // console.log("Login response:", response.data);
    if (response.success) {
      localStorage.setItem("authToken", response.data.token);
      return true;
    }
  } catch (error) {
    console.error("Login error:", error);
    throw error;
  }

  return false;
};

export const signup = async (username, password) => {
  try {
    const response = await sendPostRequest("/auth/signup", {
      username,
      password,
    });
    return response.data;
  } catch (error) {
    console.error("Signup error:", error);
    throw error;
  }
};

export const isLoggedIn = async () => {
  if (localStorage.getItem("authToken")) {
    return true;
  }
  return false;
};
