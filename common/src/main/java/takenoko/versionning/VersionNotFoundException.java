package takenoko.versionning;

public class VersionNotFoundException extends Exception {
    public VersionNotFoundException() {
        super("Error : Version not found");
    }
}
