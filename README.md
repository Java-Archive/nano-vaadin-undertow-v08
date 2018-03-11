
<center>
<a href="https://vaadin.com">
 <img src="https://vaadin.com/images/hero-reindeer.svg" width="200" height="200" /></a>
</center>


# Nano Vaadin - Ramp up in a second.
A nano project to start a Vaadin project. Perfect for Micro-UIs packed as fat jar in a docker image.

## target of this project
The target of this project is a minimal rampup time for a first hello world.
Why we need one more HelloWorld? Well, the answer is quite easy. 
If you have to try something out, or you want to make a small POC to present something,
there is no time and budget to create a demo project.
You don´t want to copy paste all small things together.
Here you will get a Nano-Project that will give you all in a second.

Clone the repo and start editing the class ```HelloWorld```.
Nothing more. 

## How does it work?
This project will not use any additional maven plugin or technology.
Core Java and the Vaadin Dependencies are all that you need to put 
a Vaadin app into a Servlet-container.

Here we are using the plain **undertow** as Servlet-Container.

As mentioned before, there is not additional technology involved.
No DI to wire all things together. The way used here is based on good old Properties.

But let´s start from the beginning.

## CoreUIService
The class CoreUIService will ramp up the Container and 
holds the Servlet- and UI- class as inner static classes.

Here all the basic stuff is done. The start will init. a ServletContainer at port **8899**.
The WebApp will deployed as **ROOT.war**. 


```java
  public static void main(String[] args) throws ServletException {
    new CoreUIService().startup();
  }

  public void startup() throws ServletException {
    DeploymentInfo servletBuilder
        = Servlets.deployment()
                  .setClassLoader(CoreUIService.class.getClassLoader())
                  .setContextPath("/")
                  .setDeploymentName("ROOT.war")
                  .setDefaultEncoding("UTF-8")
                  .addServlets(
                      servlet(
                          CoreServlet.class.getSimpleName(),
                          CoreServlet.class
                      ).addMapping("/*")
                      .setAsyncSupported(true)
                  );

    final DeploymentManager manager = Servlets
        .defaultContainer()
        .addDeployment(servletBuilder);
    manager.deploy();
    PathHandler path = path(redirect("/"))
        .addPrefixPath("/", manager.start());
    Undertow.builder()
            .addHttpListener(8899, "0.0.0.0")
            .setHandler(path)
            .build()
            .start();
  }
```

The Servlet itself will only bind the UI Class to the Vaadin Servlet.


```java
  @WebServlet("/*")
  @VaadinServletConfiguration(productionMode = false, ui = MyUI.class)
  public static class CoreServlet extends VaadinServlet {
    //customize Servlet if needed
  }
```

Now we are at6 a point that will need a little bit more flexibility.
The UI Class will be bound with a Property of an Annotation. This means that we can not 
work with dynamic content here.

The switch to the implementation will be done with a ```Supplier<Component>```
This component will be used as the Content Root of your App.


```java
  @FunctionalInterface
  public static interface ComponentSupplier extends Supplier<Component> { }

  @PreserveOnRefresh
  @Push
  public static class MyUI extends UI implements HasLogger {
    public static final String COMPONENT_SUPPLIER_TO_USE = "COMPONENT_SUPPLIER_TO_USE";
    @Override
    protected void init(VaadinRequest request) {
      final String className = System.getProperty(COMPONENT_SUPPLIER_TO_USE);
      logger().info("class to load : " + className);
      ((CheckedSupplier<Class<?>>) () -> forName(className))
          .get() //TODO make it fault tolerant
          .flatMap((CheckedFunction<Class<?>, Object>) Class::newInstance)
          .flatMap((CheckedFunction<Object, ComponentSupplier>) ComponentSupplier.class::cast)
          .flatMap((CheckedFunction<ComponentSupplier, Component>) Supplier::get)
          .ifPresentOrElse(this::setContent,
                           failed -> logger().warning(failed)
          );
    }
  }
```

First the Property **COMPONENT_SUPPLIER_TO_USE** will be used to get the name of the Supplier class.
this class will be loaded, instantiated and used to create the Supplier that will be responsible 
to create the instance of the content root.

## How a developer can use this

To use this, you can start with editing the class ```HelloWorld``` that is available inside the 
demo package at the test source folder.

```java
public class HelloWorld extends CoreUIService {

  static {
    System.setProperty(COMPONENT_SUPPLIER_TO_USE, HelloWorldSupplier.class.getName());
  }

  public static class HelloWorldSupplier implements ComponentSupplier {
    @Override
    public Component get() {
      return new Label("Hello World");
    }
  }
}
```

* extend the class ```CoreUIService```
* set the Property **COMPONENT_SUPPLIER_TO_USE** with the name of the Supplier class
* implement the interface ComponentSupplier.

After this you can start the app with the main-method inherited from ```CoreUIService```.

Happy Coding.

if you have any questions: ping me on Twitter [https://twitter.com/SvenRuppert](https://twitter.com/SvenRuppert)
or via mail.
