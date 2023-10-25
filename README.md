# NIBSS Instant Payments (NIP) Simulator

Simulate NIBSS Instant Payments (NIP) transfer operations between bank accounts.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Scheduled Operations](#scheduled-operations)
- [Configuration](#configuration)
- [Database](#database)

## Introduction

This Java web application simulates NIBSS Instant Payments (NIP) transfer operations between bank accounts. It provides REST endpoints for processing transfer requests, retrieving transactions, and generating transaction summaries.

## Features

1. **Transfer Processing**: Accept and process transfer requests.
2. **Transaction Retrieval**: Retrieve a list of transactions with optional parameters like status, userId, and date range.
3. **Scheduled Operations**: Automate tasks to update successful transactions as commission-worthy and generate daily transaction summaries.

## Project Structure

The project structure includes the following folders and key files:

- `src/`: Contains the application source code.
- `pom.xml`: The Maven project configuration file.
- `Dockerfile`: The Docker configuration file for containerization.
- `application.yml`: Application configuration properties.

## Getting Started

Follow these steps to set up and run the NIBSS Instant Payments (NIP) Simulator.

### Prerequisites

- Java 17
- Spring Boot
- Database (e.g., H2, PostgreSQL)
- Docker (for containerization)

### Installation

1. Clone this repository.

2. Configure your database in the `src/main/resources/application.yml` file.

3. Build and run the application using your preferred method.

## Usage

To use the application:

- Make POST requests to `/pay` to initiate transfer requests.
- Make GET requests to `/retrieveTransactions` to retrieve transaction details.
- Access `/generateDailySummary/{date}` to generate daily transaction summaries.

## API Endpoints

1. **Process Transfer Request**:

    - `POST /pay`: Accept and process transfer requests.

2. **Retrieve Transactions**:

    - `GET /retrieveTransactions`: Retrieve a list of transactions with optional parameters: status, userId, date range, etc.

3. **Generate Daily Transaction Summary**:

    - `GET /generateDailySummary/{date}`: Generate the summary of transactions for a specified day.

## Scheduled Operations

- A scheduled job runs at a specified time of the day to update successful transactions as commission-worthy. The commission is calculated as 20% of the transaction fee (capped at 100).

- Another scheduled job runs at a specified time of the day to produce the summary of transactions for a specified day.

## Configuration

To configure the application:

- Set environment variables, if needed.
- Customize the `application.yml` file to configure database settings and other properties.

## Database

The project relies on a database for storing transaction data. The database schema and setup instructions are as follows:

- **Schema Name**: YourDBSchema
- **Tables**:
    - `transactions`: Stores transaction data, including transaction reference, amount, transaction fee, description, date created, status, commission-worthy flag, and commission.
- Use the provided SQL script to set up the database and tables.
