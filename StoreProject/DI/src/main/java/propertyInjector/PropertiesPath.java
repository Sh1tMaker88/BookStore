package propertyInjector;

public enum PropertiesPath {
    DI("DI/src/main/resources/annotations.properties"),
    SERVER("Server/src/main/resources/myProp.properties"),
    UI("UI/src/main/resources/logger.properties");

    PropertiesPath(String path) {
        this.path = path;
    }

    String path;

    public String getPath() {
        return path;
    }
}
