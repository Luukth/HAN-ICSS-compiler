# Readme for ICSSTool
This file contains notes and issues for the ICSSTool.
For assignment instructions, see [ASSIGNMENT.md](ASSIGNMENT.md)
This tutorial is tested with Java version 13 (OpenJDK), IntelliJ and Maven. To enable instructors to assess your work you will need to keep your solution OpenJDK 13 compliant. Oracle or other distributions of the Java SDK/Runtime are not allowed.

## Running ICSSTool
ICSSTool is a `pom.xml` based, Maven-runnable application.
You can compile the application with the following command:

```mvn compile```

then run it with either

```mvn exec:java``` 
or
```mvn javfx:run```

Maven will automatically generate/update the parser from the supplied g4 file.

You can also run the application from an IDE, e.g. IntellIJ. To do so, import ICSSTool as Maven project. 
When you make changes to the .g4 file make sure you run `mvn generate-sources` prior to compiling. Most IDE's do not update the ANLTR parser automatically.

Since Java is modular, JavaFX is not bundled by default. Depending on your IDE you may need to download JavaFX and add it to your module path. See also: https://openjfx.io/openjfx-docs/

## Known issues
* Packaging works, but running the JAR standalone can be troublesome because of the JavaFX and ANLTR-runtime dependencies. You can uncomment the `maven-shade-plugin` in `pom.xml` to create a (huge) fat JAR. It removes module encapsulation which will trigger a warning.
* ICSSTool comes with tests to verify the AST based on sample input files. These are not true unit tests; they are included to help you verify your use of the AST.
