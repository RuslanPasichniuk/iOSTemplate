# Go to the project directory:
cd qa/ios

# Build the project:
mvn clean package

# Run tests with configured .env file near the Jar archive:
- java -jar target/ios-20.4.0.jar
#or
- start appium in terminal 
- then run TestExecutor.java

# Run tests with environment variables, passed in the CLI command (this variables overrides .env file data):
java -DCOMPONENT_PARAM_IOS=12.4,13.3 -DCOMPONENT_PARAM_PHONES="iPhone Xʀ,iPhone Xs Max,iPhone Xs,iPhone 6s Plus,iPhone 6s" -DCOMPONENT_PARAM_IOS_APP="/Users/user/src/test/SomeApp.app" -DCOMPONENT_PARAM_TESTNG_THREADS=2 -jar target/ios-20.4.0.jar
