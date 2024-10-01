# Key Sniffing and Uploading Service

This project is a Java-based key sniffing service that logs keystrokes and uploads the log file to a server when the program is forced to stop.

## Features

- **Key Sniffing**: Captures global key events using the JNativeHook library.
- **Logging**: Logs keystrokes to a file.
- **Shutdown Hook**: Prints a message and uploads the log file to a server when the program is forced to stop.
- **File Upload**: Uses Apache HttpClient to upload the log file to a specified server endpoint.

## Prerequisites

- Java 8 or higher
- Maven
- Internet connection (for uploading the log file)

## Dependencies

The project uses the following dependencies:

- JNativeHook
- SLF4J
- Logback
- Apache HttpClient

These dependencies are managed via Maven.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/your-repo-name.git
    cd your-repo-name
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Usage

1. Run the `App` class to start the key sniffing service:
    ```sh
    mvn exec:java -Dexec.mainClass="org.Spring.App"
    ```

2. The program will log keystrokes to a file named `log<system-name> <date>.txt`.

3. When the program is forced to stop, it will print a message and upload the log file to the server endpoint specified in the `ServerUploader` class.

## Configuration

- **Server Endpoint**: Update the `URI` variable in the `ServerUploader` class to point to your server's upload endpoint.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [JNativeHook](https://github.com/kwhat/jnativehook)
- [SLF4J](http://www.slf4j.org/)
- [Logback](http://logback.qos.ch/)
- [Apache HttpClient](https://hc.apache.org/httpcomponents-client-5.0.x/index.html)