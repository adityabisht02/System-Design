import java.util.*;

interface FilesystemObj {

    String getName();

    void printName();

    void delete();

    Directory getDirectory();

}

class File implements FilesystemObj {
    String name;
    StringBuilder content;
    Directory currentDirectory;

    File(String name, Directory currentDirectory) {
        this.name = name;
        this.currentDirectory = currentDirectory;
        content = null;
    }

    public void addContent(String newContent) {
        if (content == null) {
            content = new StringBuilder();
            content.append(newContent);

        } else {
            content.append(" " + newContent);
        }
    }

    public Directory getDirectory() {
        return currentDirectory;
    }

    public void delete() {
        currentDirectory.list.remove(this);
    }

    public void replaceContent(String newContent) {
        content = new StringBuilder(newContent);
    }

    public void readFile() {
        System.out.println(content.toString());
    }

    public String getName() {
        return name;
    }

    public void printName() {
        System.out.println(name);
    }
}

class Directory implements FilesystemObj {
    String name;
    Directory currentDirectory;
    List<FilesystemObj> list;

    Directory(String name, Directory currentDirectory) {
        this.name = name;
        this.currentDirectory = currentDirectory;
        list = new ArrayList<>();
    }

    public void add(FilesystemObj newObj) throws Exception {
        if (containsName(newObj)) {
            throw new Exception("File name " + newObj.getName() + " already exists!!", null);
        }
        list.add(newObj);
    }

    public void delete() {
        currentDirectory.list.remove(this);
    }

    public String getName() {
        return name;
    }

    public Directory getDirectory() {
        return currentDirectory;
    }

    public void printName() {
        System.out.println(name);
    }

    public boolean contains(FilesystemObj temp) {
        for (FilesystemObj obj : list) {
            if (temp == obj) {
                return true;
            }
        }
        return false;
    }

    public boolean containsName(FilesystemObj temp) {
        for (FilesystemObj obj : list) {
            if (temp.getName() == obj.getName()) {
                return true;
            }
        }
        return false;
    }

    public void ls() {
        for (FilesystemObj obj : list) {
            System.out.print(obj.getName() + " ");
        }
    }

    public void listFiles() {
        for (FilesystemObj obj : list) {
            if (obj instanceof File) {
                System.out.print(obj.getName() + " ");
            }
        }
    }

}

public class Filesystem {
    Directory root;

    Filesystem() {
        root = new Directory("", null);
    }

    public String getPathHelper(FilesystemObj target, Directory currentDir) {
        for (FilesystemObj obj : currentDir.list) {
            if (target == obj) {
                return "/" + target.getName();
            } else if (obj instanceof Directory) {
                if (contains(target, (Directory) (obj))) {
                    return "/" + obj.getName() + getPathHelper(target, (Directory) (obj));
                }
            }
        }
        return "Not found";
    }

    public void getPathUsingParent(FilesystemObj target) {

        String path = "/" + target.getName();
        Directory d = target.getDirectory();
        while (d != root && d != null) {
            path = "/" + d.getName() + path;
            d = d.getDirectory();
        }
        System.out.println(path);

    }

    public void getPath(FilesystemObj target) {
        if (target == root) {
            System.out.println("/");
        } else {
            String path = getPathHelper(target, root);
            System.out.println(path);
        }

    }

    public boolean contains(FilesystemObj target, Directory directory) {
        for (FilesystemObj obj : directory.list) {
            if (obj == target) {
                return true;
            } else if (obj instanceof Directory) {
                if (contains(target, (Directory) (obj))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsName(String name, Directory currentDir) {
        for (FilesystemObj obj : currentDir.list) {
            if (obj.getName().equals(name)) {
                return true;
            } else if (obj instanceof Directory) {
                if (containsName(name, (Directory) (obj))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getPathUsingNameHelper(String name, Directory currentDir) {
        for (FilesystemObj obj : currentDir.list) {
            if (obj.getName().equals(name)) {
                return "/" + name;
            } else if (obj instanceof Directory) {
                if (containsName(name, (Directory) (obj))) {
                    return "/" + obj.getName() + getPathUsingNameHelper(name, (Directory) (obj));
                }
            }
        }
        return "Not found";
    }

    public void getPathUsingName(String name) {
        String path = "";
        path = getPathUsingNameHelper(name, root);
        System.out.println(path);
    }

    public static void main(String[] args) {
        Filesystem fileSystem = new Filesystem();
        Directory dir = new Directory("dir", fileSystem.root);
        Directory dir2 = new Directory("dir2", dir);
        File file1 = new File("hello.txt", dir);
        File file2 = new File("hello1.txt", dir2);
        file1.addContent("hello world.");

        try {
            fileSystem.root.add(dir);
            fileSystem.root.add(file1);
        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            dir.add(file1);
            dir.add(dir2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            dir2.add(file2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        fileSystem.getPathUsingName("dir");
    }
}
