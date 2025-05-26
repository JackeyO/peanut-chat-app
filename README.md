# 🥜 Peanut Chat App

> 🚀 A modern, real-time chat application built with Spring Boot and WebSocket technology

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📖 Overview

Peanut Chat App is a feature-rich, scalable chat application that enables users to communicate in real-time. Built with modern Java technologies and following clean architecture principles, it provides a robust foundation for instant messaging applications.

## ✨ Features

### 👤 User Management
- 🔐 **JWT Authentication** - Secure token-based authentication
- 👥 **User Profiles** - Comprehensive user information management
- 🔒 **Account Security** - Password encryption and validation

### 🤝 Friend System
- ➕ **Add Friends** - Send and receive friend requests
- 📝 **Friend Requests** - Manage pending friend applications
- 👫 **Friend List** - View and manage your connections
- ❌ **Remove Friends** - End friendships when needed

### 💬 Real-time Messaging
- ⚡ **Instant Messaging** - Real-time message delivery via WebSocket
- 🏠 **Room-based Chat** - Support for group conversations
- 📱 **Message Types** - Text, images, and multimedia support
- 👍 **Message Reactions** - Like and dislike messages
- 🔄 **Message Recall** - Undo sent messages

### 🎯 Advanced Features
- 📄 **Cursor Pagination** - Efficient message history loading
- 📊 **Online Status** - Real-time user presence tracking
- 🔔 **Push Notifications** - Message delivery notifications
- 📈 **Message Queue** - Reliable message processing with MQ

## 🏗️ Architecture

```
peanut-chat-app/
├── 🖥️ peanut-chat-app-server/      # Main server application
├── 📦 peanut-chat-app-domain/      # Domain models and entities
├── 🔧 peanut-chat-app-common/      # Shared utilities and constants
├── 🏗️ peanut-chat-app-framework/   # Framework components
├── 🛠️ peanut-chat-app-utils/       # Utility classes
├── 📱 peanut-chat-app-im-client/   # IM client components
├── 🧪 peanut-chat-app-test/        # Test modules
└── 💾 sql/                        # Database scripts
```

## 🛠️ Technology Stack

| Category | Technology | Description |
|----------|------------|-------------|
| 🏗️ **Framework** | Spring Boot 3.x | Main application framework |
| 🌐 **Web** | Spring Web | RESTful API development |
| 🔐 **Security** | JWT | Token-based authentication |
| 💾 **Database** | MyBatis Plus | Object-relational mapping |
| 🚀 **Cache** | Redis | Caching and session storage |
| ⚡ **Messaging** | WebSocket | Real-time communication |
| 📮 **Queue** | RabbitMQ | Message queue processing |
| 🔧 **Build** | Maven | Dependency management |

## 🚀 Quick Start

### 📋 Prerequisites

- ☕ Java 17 or higher
- 📦 Maven 3.6+
- 🐳 Docker (optional, for Redis/MySQL)
- 💾 MySQL 8.0+
- 🔄 Redis 6.0+

### 🔧 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/peanut-chat-app.git
   cd peanut-chat-app
   ```

2. **Setup Database**
   ```bash
   # Import SQL scripts
   mysql -u root -p < sql/init.sql
   ```

3. **Configure Application**
   ```bash
   # Copy and modify configuration
   cp application.yml.example application.yml
   # Edit database and Redis connection settings
   ```

4. **Build and Run**
   ```bash
   # Build all modules
   mvn clean install
   
   # Run the application
   cd peanut-chat-app-server
   mvn spring-boot:run
   ```

5. **Access the Application**
   - 🌐 API Base URL: `http://localhost:8080`
   - 📚 API Documentation: `http://localhost:8080/swagger-ui.html`

## 📚 API Documentation

### 👤 User APIs
```http
GET  /peanut/chat/user/profile    # Get user profile
POST /peanut/auth/login           # User login
POST /peanut/auth/register        # User registration
```

### 🤝 Friend APIs
```http
POST /friend/apply                # Send friend request
GET  /friend/ack                  # Accept/reject friend request
GET  /friend/list                 # Get friend list
```

### 💬 Chat APIs
```http
POST /peanut/chat/send            # Send message
POST /peanut/chat/msg/page        # Get message history
GET  /peanut/chat/msg/likeOrDislike # React to message
```

### 🏠 Room APIs
```http
GET  /peanut/room/info            # Get room information
```

## 🔧 Configuration

### 🗄️ Database Configuration
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/peanut_chat
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:password}
```

### 🔄 Redis Configuration
```yaml
spring:
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
    password: ${REDIS_PASSWORD:}
```

### 🔐 JWT Configuration
```yaml
jwt:
  secret: ${JWT_SECRET:your-secret-key}
  expiration: ${JWT_EXPIRATION:86400}
```

## 🏗️ Development

### 📁 Project Structure

```
src/main/java/com/sici/chat/
├── 🎮 controller/          # REST Controllers
├── 🔧 service/             # Business Logic Services
├── 💾 dao/                 # Data Access Objects
├── 📦 model/               # Domain Models
├── 🛠️ util/                # Utility Classes
├── ⚙️ config/              # Configuration Classes
├── 🚨 exception/           # Custom Exceptions
└── 🔌 handler/             # Message Handlers
```

### 🎯 Key Components

- **🎮 Controllers**: Handle HTTP requests and responses
- **🔧 Services**: Implement business logic
- **💾 DAOs**: Manage data persistence
- **📦 Models**: Define data structures
- **🚨 Exception Handling**: Centralized error management
- **🔐 Security**: JWT-based authentication

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific test module
cd peanut-chat-app-test
mvn test

# Generate test coverage report
mvn jacoco:report
```

## 📊 Monitoring

### 🔍 Health Check
```http
GET /actuator/health
```

### 📈 Metrics
```http
GET /actuator/metrics
```

## 🚀 Deployment

### 🐳 Docker Deployment
```bash
# Build Docker image
docker build -t peanut-chat-app .

# Run with Docker Compose
docker-compose up -d
```

### ☁️ Production Deployment
1. Build production package: `mvn clean package -Pprod`
2. Upload to server
3. Configure environment variables
4. Run: `java -jar peanut-chat-app-server.jar`

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. 🍴 Fork the repository
2. 🌿 Create a feature branch: `git checkout -b feature/amazing-feature`
3. 💾 Commit your changes: `git commit -m 'Add amazing feature'`
4. 📤 Push to the branch: `git push origin feature/amazing-feature`
5. 🎯 Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **JackeyO** - *Initial work* - [YourGitHub](https://github.com/JackeyO)

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- MyBatis team for the excellent ORM solution
- All contributors who helped improve this project

## 📞 Support

If you have any questions or need help, please:

- 📧 Email: 749291su@gmail.com
- 💬 Join our Discord: [Peanut Chat Community](https://discord.gg/peanutchat)
- 🐛 Report bugs: [GitHub Issues](https://github.com/JackeyO/peanut-chat-app/issues)

---

<div align="center">
  <h3>🌟 If you like this project, please give it a star! 🌟</h3>
  <p>Made with ❤️ by the Peanut Chat Team</p>
</div> 