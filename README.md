# Buy IT API 

## Table of contents
* [Preconditions](#preconditions)
   * [Windows](#windows)
   * [Mac OS](#macos)
* [Run using IDE](#idea)
* [Build and run using maven](#build-maven)
* [Build and run using docker](#build-docker)
* [Validate and test using maven](#test)

## Preconditions
### Windows
##### Install and Setup Java
1. Install Java 8 or higher on your local machine. To download Java 8 jdk click on [that](https://java.com/ru/download/) and install java for Windows.
1. To set up JAVA_HOME follow the next steps:
   1. Click the left button of the mouse on the 'My computer' icon and choose 'Settings' in the drop-down list;
   1. In the 'System' window click 'Additional parameters of the system';
   1. Click on the button 'Environment variables';
   1. In the modal window 'System variables' click button 'Add';
   1. In the field 'Variable name' fill 'JAVA_HOME';
   1. In the field 'Variable value' fill <path to jdk>;
   1. Click the 'OK' button;
   1. Open 'PATH' in the 'System variables';
   1. At the bottom of the list add %JAVA_HOME%\lib, %JAVA_HOME%\bin and %JAVA_HOME%\jre;
1. To check the acceptance of changes, open the command line of your machine and run the following 'path' command, at the end of the change you need to set <path to jdk>\lib,<path to jdk>\bin and <path to jdk>\jre
### Mac OS
1. Install with Homebrew
   1. Install Homebrew using the command (*if the homebrew already installed into you mac os skip that step*) ``/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"``
   1. Install java 8 using command `brew tap adoptopenjdk/openjdk && brew cask install adoptopenjdk8`
   1. Set JAVA_HOME using command `export JAVA_HOME=/path/to/java` (*usually for homebrew install it would be /Library/Java/JavaVirtualMachines/jdk/Contents/Home*)
   1. Set JAVA_HOME to the patch using command `PATH=$JAVA_HOME/lib:$JAVA_HOME/bin:$JAVA_HOME/jre:$PATH`

<h2 id="idea">Run using IDE</h2>

* Install `IntelliJ IDEA` IDE
* Install _Lombok Plugin_: `IntelliJ IDEA > Preferences > Plugins > Browse repositories > Lombok Plugin`
* _Enable annotation processing_: `IntelliJ IDEA > Preferences > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing`
* Checkout project from git
* Start import as `Maven` project
* Open `Application.class` in the _com.afj.solution.common_ and click on the class and choose `Modify Run Configuration`
* Insert <a href="#env">Environment Variables</a>
* Open `http://localhost:80/swagger-ui/#` to see the service endpoints

<h2 id="build-maven">Build and run using maven</h2>

_*NOTE!*_ Supports only for Linux/Mac_OS
Example: `export DATABASE_URL=localhost:8761 && export ... && ./mvnw clean spring-boot:run`(In the export insert all <a href="#env">Environment Variables</a>)
* Open `http://localhost:80/swagger-ui/#` to see the service endpoints

<h2 id="build-docker">Build and run using docker</h2>

* <a href="https://www.docker.com/products/docker-desktop">Install Docker</a>;

<h2 id="env">Environment variables</h2>

- [ ] \(Required) DATABASE_URL - Database url to connect service to db. Example `jdbc:mysql://localhost:3307/app`
- [ ] \(Required) DATABASE_USERNAME - Database service username to connect to the db
- [ ] \(Required) DATABASE_PASSWORD - Database service password to connect to the db
