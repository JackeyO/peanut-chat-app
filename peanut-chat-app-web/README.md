# Vue 3 + TypeScript + Vite

This template should help get you started developing with Vue 3 and TypeScript in Vite. The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

Learn more about the recommended Project Setup and IDE Support in the [Vue Docs TypeScript Guide](https://vuejs.org/guide/typescript/overview.html#project-setup).

## API Configuration

The application uses a centralized HTTP and WebSocket service structure:

- API base URLs are configured in environment variables (`.env`, `.env.development`, `.env.production`)
- HTTP requests use a common Axios wrapper (`src/utils/http.ts`)
- WebSocket connections use a centralized service (`src/utils/websocket.ts`)
- API endpoints are defined in their respective modules and use the HTTP service

### Communication Protocol Responsibilities

- **HTTP (REST API)**: All chat message sending and retrieval operations use HTTP POST
- **WebSocket**: Only used for login operations and receiving real-time notifications

### Environment Configuration

- Development: Set URLs in `.env.development`
- Production: Set URLs in `.env.production`
- Default fallback: Values in `.env`

Example:
```
VITE_API_BASE_URL=http://localhost:9999
VITE_WS_BASE_URL=ws://localhost:9999
```
