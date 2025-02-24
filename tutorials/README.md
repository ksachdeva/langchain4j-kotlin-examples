## Running examples from the command line

```bash
./gradlew run -PmainClass=<name_of_main_class>

# example
./gradlew run -PmainClass=_00_HelloWorld
```

```bash
# for examples that require arguments
./gradlew run -PmainClass=<name_of_main_class> --args="<arguments>"
# example
./gradlew run -PmainClass=_03_PromptTemplate --args="simple"
```