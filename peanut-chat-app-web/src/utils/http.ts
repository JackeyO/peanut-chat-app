import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import axios from 'axios';

// Base URLs from environment variables
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:9999';

// Request methods
enum RequestMethod {
  GET = 'get',
  POST = 'post',
  PUT = 'put',
  DELETE = 'delete',
  PATCH = 'patch'
}

// Response interface
interface HttpResponse<T = any> {
  code: number;
  message: string;
  data: T;
}

// HTTP service class
class HttpService {
  private instance: AxiosInstance;
  
  constructor(config: AxiosRequestConfig = {}) {
    // Create axios instance
    this.instance = axios.create({
      baseURL: API_BASE_URL,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json',
        ...config.headers
      },
      ...config
    });
    
    // Request interceptor
    this.instance.interceptors.request.use(
      (config) => {
        // Add authorization token if exists
        const token = localStorage.getItem('chat_token');
        if (token) {
          config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );
    
    // Response interceptor
    this.instance.interceptors.response.use(
      (response: AxiosResponse) => {
        return response;
      },
      (error) => {
        // Handle error cases (401, 404, 500, etc.)
        if (error.response) {
          const { status } = error.response;
          switch (status) {
            case 401:
              // Handle unauthorized
              console.error('Unauthorized access');
              // Redirect to login or refresh token
              break;
            case 404:
              console.error('API not found');
              break;
            case 500:
              console.error('Server error');
              break;
            default:
              console.error(`Request failed with status code ${status}`);
          }
        } else if (error.request) {
          console.error('No response from server');
        } else {
          console.error('Request configuration error:', error.message);
        }
        return Promise.reject(error);
      }
    );
  }
  
  // Generic request method
  private async request<T = any>(
    method: RequestMethod,
    url: string,
    data?: any,
    config?: AxiosRequestConfig
  ): Promise<T> {
    try {
      const response = await this.instance({
        method,
        url,
        data: method !== RequestMethod.GET ? data : null,
        params: method === RequestMethod.GET ? data : null,
        ...config
      });
      
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  }
  
  // HTTP methods
  public async get<T = any>(url: string, params?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.request<T>(RequestMethod.GET, url, params, config);
  }
  
  public async post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.request<T>(RequestMethod.POST, url, data, config);
  }
  
  public async put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.request<T>(RequestMethod.PUT, url, data, config);
  }
  
  public async delete<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.request<T>(RequestMethod.DELETE, url, data, config);
  }
  
  public async patch<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.request<T>(RequestMethod.PATCH, url, data, config);
  }
}

// Create and export default instance
const http = new HttpService();
export default http;

// Export class for creating custom instances
export { HttpService };

