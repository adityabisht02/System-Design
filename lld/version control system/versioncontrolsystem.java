import java.util.*;

class VCObject {
    List<File> versions;

    VCObject() {
        versions = new ArrayList<>();
    }
}

class File {
    String title;
    String content;

    File(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

public class versioncontrolsystem {
    List<VCObject> list;

    public static void main(String[] args) {

    }
}
