# Java Dynamic Exe-Graph
- This is a Java implementation of a dynamic execution graph. 
- The graph is built by analyzing the execution of a program and the graph is updated as the program executes. 
- Using Java Virtual Machine Tool Interface (JVMTI) to monitor the execution of the program and build the graph.
- In this program, feel free to modify the granularity in [StepDebug](src/main/java/org/example/utils/StepDebug.java) to reduce/increase the detail of the graph.
- The graph is visualized using the [Graphviz](https://graphviz.org/) library.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
- Java 17
- Maven
- Graphviz

### Usage
1. Clone the repository
2. Fix the classpath(in StepDebug.java, line 52) and Run Maven to build the project.
3. Create a new black "__input__" file in the root directory of the project
4. Write any input information in the "__input__" file
5. Run Command
```shell
java -jar target/java-dynamic-exe-graph-1.0-SNAPSHOT.jar <Main class name>
dot -Tpng graph.dot -o graph.png
```
6. The graph will be generated in the root directory of the project

### Example
```shell
java -jar target/java-dynamic-exe-graph-1.0-SNAPSHOT.jar 
dot -Tpng graph.dot -o graph.png
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## More Information
- Repository: [https://github.com/dududuguo/java-dynamic-exe-graph.git](https://github.com/dududuguo/java-dynamic-exe-graph.git)
- The project has used in UTS:48024 Programming 2
- Author: zhihao guo

