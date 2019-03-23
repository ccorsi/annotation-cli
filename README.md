# Welcome to an Annotated Command Line Option Library #

This is a simple project that allows you to define command line arguments using annotations 
and be able to have them set implicitly instead of explicitly by the application.  

The other objective was to be able to set multiple instances and not be relegated to a single instance.  This 
removes the need to have the application concern itself with setting these values.

The final objective was to be able to set objects besides the primitive types or their wrapper classes, String and 
atomic integer and long instances.  This is done using the class string value constructor.

The user can start to use this tool by assigning the Option annotation to methods that you want to be part of the
command line options.  The Option annotation provides different settings that can be defined to inform and describe
how the command line option can be set and its functionality.

For instance, here is an example of a method where the Option annotations has been assigned.

    import org.valhalla.cli.annotations.Option;

    public class Main {

    ....

       @Option(shortName = 'D', longName = "directory" defaultValue=".")
       public void setDirectory(File directory) {
       ....
       }

    ....

       public static void main(String args[]) {
          Options options = new Options(new Class<?>[] { Main.class });
          String remaining[] = options.processArguements(args);
          ...
       }
   
    ....

    }


As you can see, the Main contains a command line option in _short_ and _long_ format, __-D <value> | --directory==<value>__, 
that can be set by some user using this application.  You can also include a description about the particular command
line option using the _description_ attribute.  The developer can also define an _embedded_ command line option.  For
example the gcc -O option is an example of an embedded option that only expects a single character option like -O2.

You can also create multiple classes that contain command line options that will be include as part of your
application.  You have two options on how these are integrated within your application.  You can pass all of those
class definitions to the Options constructor or you can create public methods that return references to those instances
and have those methods annotated with the OptionReference annotation.  For instance,

    import org.valhalla.cli.annotations.Options;

    public DirectoryOptions {

      @Option(shortName = "D")
      public void setDirectory(File dir) {
       ...
      }
  
      ...
  
    }

    import org.valhalla.cli.annotations.OptionReference;

    public class Main { 

    ....

       private DirectoryOptions directoryOptions;
   
       @OptionReference
       public DirectoryOptions getDirectoryOptions() {
         return directoryOptions;
       }
   
       ...
   
    }

The _Main_ will inherit the command line options that are part of the _DirectoryOptions_ as well as all of the other 
command line options that the Main and other classes passed to the Options constructor.  This greatly
simplifies the process by combining and maintaining the command line options associated to a particular application.
