# Aquarium Security API

## About
The Aquarium Security API is a project designed for studying the integration between Keycloak and a Java-based security API. It will be used in a system called **Aquarium**, which consists of a frontend, a Backend-for-Frontend (BFF), and multiple backend services. The Security API will be responsible for providing valid application tokens for all resources, including the frontend, BFF, and backends.

---

## Building and Loading Keycloak with Docker Compose

To start a local Keycloak server using Docker Compose, you can use a `docker-compose.yml` file created on this src folder.


Run the following command to start the Keycloak server:
```bash
docker-compose up -d
```

---

## Accessing Keycloak
1. Open your Keycloak Admin Console by navigating to:
   ```
   http://localhost:8080/admin
   ```
2. Log in with your admin credentials.

---

## Creating the Client
1. Go to **Clients** → **Create**.
2. Fill in the required fields:
    - **Client ID:** `bff-client`
    - **Client Protocol:** `openid-connect`
    - **Access Type:** `Confidential`
3. Click **Save**.

---

## Configuring Client Credentials (Client Secret)
1. After creating the client, navigate to the **Credentials** tab.
2. Set the **Client Authenticator** to `Client ID and Secret`.
3. Copy the generated **Client Secret**. This will be used in the API requests.

---

## Configuring the Client Credentials Flow (Machine-to-Machine)
To enable **Machine-to-Machine** authentication using the **client credentials flow**:

1. Go to the **Client Settings** tab.
2. Ensure the following settings:
    - **Standard Flow Enabled:** `Off`
    - **Direct Access Grants Enabled:** `Off`
    - **Service Accounts Enabled:** `On`
3. Assign proper roles and scopes in the **Service Account Roles** tab to control the access permissions.

---

## Token Generation Example (Postman Request)
To generate a token using Postman:

- **Method:** `POST`
- **URL:** `http://localhost:8080/realms/aquarium/protocol/openid-connect/token`
- **Headers:**
    - `Content-Type`: `application/x-www-form-urlencoded`
- **Body (x-www-form-urlencoded)**:
  ```
  grant_type=client_credentials
  client_id=bff-client
  client_secret=YOUR_CLIENT_SECRET
  ```

### Response Example:
```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expires_in": 3600
}
```

The `access_token` can now be used to authenticate requests across the secured services.

---

## Project Structure
```plaintext
src
├── main
│   ├── java
│   │   └── com.credify
│   │       ├── domain.model
│   │       │   ├── TokenRequest.java
│   │       │   └── TokenResponse.java
│   │       ├── exception
│   │       │   ├── GlobalExceptionHandler.java
│   │       │   └── InvalidTokenException.java
│   │       ├── infrastructure
│   │       │   ├── config
│   │       │   │   └── SecurityConfig.java
│   │       │   └── controller
│   │       │       └── ClientAuthController.java
│   │       └── services
│   │           ├── ClientAuthService.java
│   │           └── ClientAuthServiceImpl.java
│   │       └── CredentialManagementApiApplication.java
│   └── resources
│       └── application.yml
└── test
```

- **domain.model:** Contains DTOs for token requests and responses.
- **exception:** Custom exception handling classes.
- **infrastructure:** Configuration and controllers for security integration.
- **services:** Business logic for client authentication.
- **resources:** Application configuration.

---

## License
This project is for educational purposes only and not intended for production use.

