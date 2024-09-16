

# Java Client-Server Application

This repository contains a basic **Client-Server** application built using **Java**. It demonstrates the core concepts of networking in Java, where the server listens for requests from the client, and the client sends requests and processes responses from the server.

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Usage](#usage)
- [Files](#files)
- [Requirements](#requirements)
- [Installation](#installation)
- [Contributors](#contributors)
- [License](#license)

## Description

This project is designed to help users understand how client-server communication works in Java. The server listens on a specific port for client requests and responds accordingly. The client connects to the server and can send requests, which the server processes and sends back responses.

**Key Topics Covered**:
- Networking in Java
- Client-Server architecture
- Socket programming

## Features

- Basic client-server communication.
- Socket creation and handling in Java.
- Simple request-response mechanism between client and server.

## Usage

### Running the Server
1. Compile the `Server.java` file:
   ```bash
   javac Server.java
   ```
2. Run the server:
   ```bash
   java Server
   ```

### Running the Client
1. Compile the `Client.java` file:
   ```bash
   javac Client.java
   ```
2. Run the client:
   ```bash
   java Client
   ```

### Expected Output
- The server will wait for client requests.
- The client can send requests, and the server will respond accordingly.

## Files

- **Client.java**: This file contains the implementation of the client that connects to the server and communicates with it.
- **Server.java**: This file contains the implementation of the server that listens for client requests and responds.

## Requirements

- **Java 8 or higher**
- **Basic knowledge of Java and socket programming**

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/YourUsername/repository-name.git
   ```
2. Navigate to the project directory:
   ```bash
   cd repository-name
   ```
3. Follow the [Usage](#usage) section for compiling and running the project.

## Contributors

- **Shrey Lakhtaria**: [LinkedIn Profile](https://www.linkedin.com/in/shrey-lakhtaria)  
Third-semester Computer Science Engineering student at CSPIT, CSE department.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
