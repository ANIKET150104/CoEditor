# CoEditor

CoEditor is a **Spring Boot–based collaborative code editor** with:

- 🔑 **JWT authentication** (register & login)  
- 📝 **Collaborative editing** via WebSockets (room-based)  
- ⚡ **Code execution** using the **Judge0 API**  
- 🎨 **JTE templates + htmx** for UI  
- 🗄️ **MySQL persistence** via Spring Data JPA  

---

## 🚀 Tech Stack

- **Java 21**  
- **Spring Boot 3.5.4**  
  - Spring Web + WebSocket  
  - Spring Data JPA (MySQL)  
  - Spring Security (JWT)  
- **Judge0 API** (remote code execution)  
- **JTE** templates + **htmx**  
- **JJWT 0.12.5** (JWT handling)  
- **dotenv-java** (.env config)  
- **Lombok**  

---

## 📂 Project Structure

CoEditor/
├─ src/main/java/... # Controllers, services, config
├─ src/main/resources/...# templates, static assets, config
├─ jte-classes/ # Generated JTE classes
├─ pom.xml # Maven build config
└─ Dockerfile # Container build

yaml
Copy code

---

## ⚙️ Configuration

`.env` file in the project root:

```dotenv
MYSQL_URL=jdbc:mysql://localhost:3306/coeditor
MYSQL_USERNAME=coeditor
MYSQL_PASSWORD=coeditor
JWT_SECRET=replace-with-strong-secret
```

## 🛠️ Build & Run
bash
Copy code
./mvnw clean package
./mvnw spring-boot:run
App runs at: http://localhost:8080

## 🔗 Endpoints
### 🌐 View Pages
| Method	|     Path        |   	Description      |
|---------|-----------------|----------------------|
| GET     |	`/` or `/login` |	Render login page    |
|---------|-----------------|----------------------|
| GET     |	`/register`     |	Render register page |
|---------|-----------------|----------------------|
| GET     |	`/register`     |	Render login page    |


### 🔑 Authentication (JWT REST API)
**Base Path:** `/auth`

| Method | 	 Path    |	           Body                         |	Response           |
|---- ---|-----------|------------------------------------------|--------------------|
| POST	 |`/register`| { "username": "...", "password": "..." } |	{ "token": "..." } |
|---- ---|-----------|------------------------------------------|--------------------|
| POST	 | `/login`  | { "username": "...", "password": "..." } |	{ "token": "..." } |

**Use token for protected requests:**

```http
Authorization: Bearer <jwt-token>
```
### ⚡ Code Execution (Judge0 API Proxy)
**Base Path:** `/run`

Run code (form submission)
```
http

POST /run
Content-Type: application/x-www-form-urlencoded

code=System.out.println("Hello");&languageId=62&stdin=input
languageId: Judge0 language ID (e.g., 62 = Java 17)

stdin (optional): Input for program

Response (JSON or plain text depending on Accept header):

json

{
  "stdout": "Hello\n",
  "stderr": null,
  "status": "Accepted"
}
```
### ✍️ Collaborative Editing (WebSocket)
#### STOMP WebSocket Endpoints

**Connect to:** ws: `//localhost:8080/ws`

**Subscriptions:**

`/topic/{roomId}` → broadcast updates

**Messages sent to:**

`/app/editor/{roomId}` → push code updates

`/app/getLatestCode/{roomId}` → request latest state

**Message Types**
  |   Type     	 |           Description                              |
  |--------------|----------------------------------------------------|
  | CODE_UPDATE  |	Broadcasts updated code (persists + sends to room)|
  |--------------|----------------------------------------------------|
  | LANG_CHANGE	 | Updates programming language for the room          |
  |--------------|----------------------------------------------------|
  | REQUEST_FULL | Client asks for full sync of code state            |
  |--------------|----------------------------------------------------|
  | FULL_SYNC	   | Server response with latest code state             |

**Example:** Client sends

json
```
{
  "type": "CODE_UPDATE",
  "code": "print('Hello')",
  "language": "python"
}

Server broadcasts

json

{
  "type": "CODE_UPDATE",
  "user": "alice",
  "code": "print('Hello')",
  "language": "python"
}
```
## 🧪 Testing
```
./mvnw test
```

## ✅ Production Checklist
**Strong JWT_SECRET**

**Secure MySQL with SSL**

**Set SPRING_PROFILES_ACTIVE=prod**

**Use reverse proxy (e.g., Nginx) with HTTPS**

## 📄 License
This project currently has no license file. Add one before distributing.
