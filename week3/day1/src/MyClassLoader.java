//自定义类加载器
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 自定义类加载器：加载本地指定路径下的.class文件
 * 核心：继承ClassLoader，重写findClass方法
 */
public class MyClassLoader extends ClassLoader {
    // 自定义类加载器的根路径：存储.class文件的目录（需修改为自己的Hello.class所在目录）
    private String rootPath;

    // 构造方法：传入.class文件的根路径
    public MyClassLoader(String rootPath) {
        // 父类加载器默认是应用程序类加载器（ClassLoader的默认构造方法会自动设置）
        this.rootPath = rootPath;
    }

    /**
     * 重写findClass方法：实现自定义的类加载逻辑
     * @param name 类的全限定名（无包名则直接写类名，如Hello；有包名如com.test.Hello）
     * @return 加载后的Class对象
     * @throws ClassNotFoundException 类未找到异常
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // 1. 根据类名和根路径，获取.class文件的字节数组
            byte[] classBytes = loadClassBytes(name);
            if (classBytes == null || classBytes.length == 0) {
                throw new ClassNotFoundException("类字节流读取失败：" + name);
            }
            // 2. 调用父类的defineClass方法，将字节数组转为Class对象（核心）
            // 参数1：类的全限定名；参数2：字节数组；参数3：起始索引；参数4：字节数组长度
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("自定义类加载器加载失败：" + name, e);
        }
    }

    /**
     * 辅助方法：读取.class文件，转化为字节数组
     * @param name 类的全限定名
     * @return 类的字节数组
     * @throws IOException 输入输出异常
     */
    private byte[] loadClassBytes(String name) throws IOException {
        // 将类的全限定名的.替换为/，适配文件路径（如com.test.Hello → com/test/Hello）
        String className = name.replace(".", "/");
        // 拼接.class文件的完整路径：根路径 + 类名 + .class
        String classFilePath = rootPath + "/" + className + ".class";

        // 读取文件字节流：用FileInputStream读取，ByteArrayOutputStream拼接字节数组
        try (InputStream is = new FileInputStream(classFilePath);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024]; // 缓冲区，每次读取1024字节
            int len;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len); // 将读取的字节写入输出流
            }
            return bos.toByteArray(); // 将输出流转为字节数组
        }
    }

    // 测试主方法：使用自定义类加载器加载Hello类，实例化并调用方法
    public static void main(String[] args) throws Exception {
        // 1. 创建自定义类加载器实例，传入.class文件的根路径（修改为自己的路径，如D:/JVMTest/）
        MyClassLoader myClassLoader = new MyClassLoader("C:\\Users\\jyc\\IdeaProjects\\learn\\week3\\day1\\src");

        // 2. 调用loadClass方法加载Hello类（遵循双亲委派机制）
        Class<?> helloClass = myClassLoader.loadClass("Hello");

        // 3. 通过反射实例化类（Hello类无参构造）
        Object helloInstance = helloClass.newInstance();

        // 4. 通过反射调用sayHello方法
        helloClass.getMethod("sayHello").invoke(helloInstance);

        // 5. 验证Class对象的类加载器是否为自定义类加载器
        System.out.println("Hello类的Class对象的类加载器：" + helloClass.getClassLoader().getClass().getName());
    }
}