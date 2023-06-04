<h1 align="center">
  SA Banking System
  <br>
</h1>

<p align="center">
  • <a href="#-project-setup-and-commands">FTN Novi Sad, 2023</a>
  •
</p>


## 👨‍💻 Developer
    • Anastasija Samčović   SW44-2019
    • Srđan Đurić           SW63-2019

## 🚀 Project setup

#### <span style="vertical-align: middle">:warning:</span> *Pre requirements:*

- Node.js
- Angular
- JDK version 11
- PostgreSQL
- Drools plugin for InteliJ

#### <span style="vertical-align: middle">:floppy_disk:</span> *How to run backend:*

- Backend consists of three separate projects:
    - Model project(all entities and enums)
    - Kjar project(all drools rules)
    - Service project(controllers, services and repositories)
- <b>First step:</b>
    - Open Model application in InteliJ IDE
    - Click on Maven option and run <b>Clean</b>
    - After Clean run <b>Install</b>
- <b>Second step:</b>
    - Open Kjar application in InteliJ IDE
    - Click on Maven option and run <b>Clean</b>
    - After Clean run <b>Install</b>
- Open Service application and click run button

#### <span style="vertical-align: middle">:floppy_disk:</span> *How to run frontend:*

- Open front-app in wanted IDE (VSCode, WebStorm etc.)
- Run <b>npm install --force</b> in terminal to install all needed dependencies
- Run <b>ng serve</b> in terminal to start application

## 🤝 Useful to know:
- There are two types of user roles to login:
    - Admin (admin@gmail.com)
    - Client (srdjan@gmail.com)
- Password for all users is: sifra1234A2@
- <b>Note that you will need to change configuration to connect to your local PostgreSQL Database</b>

## 📱 Some photos of application
![registration page](./front-end/src/assets/images/S1.jpg)
![home page](./front-end/src/assets/images/S2.jpg)
![credit card request page](./front-end/src/assets/images/S3.jpg)
![accepted cash credit page](./front-end/src/assets/images/S4.jpg)
![reports page](./front-end/src/assets/images/S5.jpg)
