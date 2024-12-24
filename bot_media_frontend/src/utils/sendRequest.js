import axios from "axios";

const BASE_URL = "http://localhost:8080";
const DEFAULT_HEADERS = {
  'Content-Type': 'application/json'
};

const sendRequest = async ({
  method = 'GET',
  path = '/',
  body = null,
  headers = {},
  params = {}
} = {}) => {
  try {
    const token = localStorage.getItem('authToken');
    const config = {
      method,
      url: `${BASE_URL}${path}`,
      headers: {
        ...DEFAULT_HEADERS,
        ...headers,
        ...(token && { Authorization: `Bearer ${token}` })
      },
      params
    };

    if (body) {
      config.data = body;
    }

    const response = await axios(config);
    return {
      success: true,
      data: response.data
    };
  } catch (error) {
    return {
      success: false,
      error: error.response?.data || error.message
    };
  }
};

export const sendGetRequest = (path, params = {}) => 
  sendRequest({ method: 'GET', path, params });

export const sendPostRequest = (path, body = {}, headers = {}) => 
  sendRequest({ method: 'POST', path, body, headers });

export const sendPutRequest = (path, body = {}, headers = {}) => 
  sendRequest({ method: 'PUT', path, body, headers });

export const sendDeleteRequest = (path, params = {}) => 
  sendRequest({ method: 'DELETE', path, params });

export default sendRequest;
