#  <img src=".\src\icon\icon.png" alt="icon" style="zoom:25%;" />  实验报告

## 一、引言

### 1. 开发任务介绍

#### 1.1 基础功能需求

* 设计良好的图形用户界面，界面中要求至少有默认大小的三角形、方框、圆形、椭圆、连接线等五种元素可供用户选择后，绘制到画布上；

* 允许用户添加文字描述；

* 单击可以选中图形，并允许对图形的拷贝复制；

* 多个图形可以组合，组合后的图形同样有拷贝复制的功能；

*  支持撤销上一步操作的功能。

#### 1.2 扩展功能需求

*  右键点击图形可通过文本框对图形的尺寸进行调整；

* 支持个性化界面，包括设置界面字体大小、界面皮肤等；

* 支持图形（包括组合图形）的拖拽调整图形大小；

* 支持撤销多步的功能；

* 设计一种硬盘文件存储格式可以保存用户绘制的图形，并可以加载。

#### 1.3 额外实现

* 支持拖拽移动图形，包括组合图形的移动；
* 可以自定义设置画笔颜色；
* 支持导出图片。

### 2. 核心技术简介

#### 2.1 Java Swing

本项目采用`Java Swing`来实现GUI，`Swing`是一个为Java设计的GUI工具包，属于Java基础类的一部分。是一个基于Java的跨平台MVC框架，并且使用了高度模块化的架构，可以通过接口的方式使用各种定制框架来扩展`Swing`。

#### 2.2 设计模式

本项目主要采用了4种设计模式：

* 单例模式
* 原型模式
* 命令模式
* 观察者模式



## 二、分析设计

### 1. 整体结构

项目的目录结构如下：

```
├─cn
│  └─edu
│      └─nju
│          │  Start.java
│          │
│          ├─Command
│          │      ChangeSizeCommand.java
│          │      Command.java
│          │      CreateCommand.java
│          │      DeleteCommand.java
│          │      MoveCommand.java
│          │      PasteCommand.java
│          │
│          ├─shape
│          │      AbstractShape.java
│          │      Ellipse.java
│          │      Line.java
│          │      RectAngle.java
│          │      Shape.java
│          │      Text.java
│          │      TriAngle.java
│          │
│          └─widget
│                  MainWindow.java
│                  MyColorPanel.java
│                  MyDrawPanel.java
│                  MyMenuBar.java
│                  MyStatusBar.java
│                  MyToolBar.java
│                  Observer.java
│                  Operation.java
│                  Subject.java
│
├─icon
|	.png
|	.
|	.
```

下面按照软件包的划分介绍我的设计思路。

### 2. Shape包

首先定义图形的抽象类`AbstractShape`，抽象方法有：

* `draw`绘制函数
* `contains`判断点是否在图元内部
* `update`更新图元位置坐标
* `changeSize`改变图元大小

具体图形类有四种：`Ellipse`椭圆（也就是圆）类，`RectAngle`矩形类，`TriAngle`三角形类，`Line`直线类，`Text`文本类。

> `Shape`类主要用于表示图元的位置信息

具体图元的绘制则使用`java.awt.geom`包中2D图形的`draw`方法，以椭圆为例：

```java
public class Ellipse extends AbstractShape{
    private Ellipse2D.Double ellipse; //geom包中的Ellipse2D
    // ...
    @Override
    public void draw(Graphics2D g2d) {
        // ...
        g2d.draw(ellipse);
    }
}
```

UML类图如下：

<img src=".\reportImage\shapeUML.png" alt="shapeUML" style="zoom:50%;" />

> 抽象图形类继承自`Serializable`是为了实现深克隆`deepClone`，这是**原型模式**。

### 3. Widget包

显然设计GUI用`JFrame`来创建窗口，窗口上放各种`JPanel`组件即可。本项目中类`MainWindow`就继承自`JFrame`，上面有组件：

* 画布`MyDrawPanel`
* 工具栏`MyToolBar`
* 取色盘`MyColorPanel`

均继承自`JPanel`类。

窗口的设置还有菜单栏`MyMenuBar`，继承自`JMenuBar`。

还有`Operation`类，是为了实现撤销操作的**命令执行者**。

#### 3.1 JPanel组件

显然设计GUI用`JFrame`来创建窗口，窗口上放各种`JPanel`组件即可。本项目中类`MainWindow`就继承自`JFrame`，上面有组件：

* 画布`MyDrawPanel`
* 工具栏`MyToolBar`
* 取色盘`MyColorPanel`

均继承自`JPanel`类

##### 3.1.1 画布MyDrawPanel

大部分操作都是在使用鼠标在画布上进行的，因此需要重写鼠标事件。`MyDrawPanel`内有大量为了交互而设置地字段和方法，这里不赘述了。关键的字段有：

* `ArrayList<AbstractShape> shapes`来存储画布上的所有图形。
* 同时为了实现撤销操作，需要用`ArrayList<Command> undoCmds`来存储所有命令。

UML类图如下：

<img src=".\reportImage\drawPanelUML.png" alt="shapeUML" style="zoom:50%;" />

##### 3.1.2 取色盘和工具栏

取色盘`MyColorPanel`主要作用就是提取颜色，设置`MainWindow`中的`penColor`字段，UML类图如下：

<img src=".\reportImage\colorPanelUML.png" alt="shapeUML"/>

工具栏`MyToolBar`上面有一系列`JButton`按钮，获取点击来设置`MainWindow`中的`currentMode`字段，UML类图如下：

<img src=".\reportImage\toolBarUML.png" alt="shapeUML"/>

> 可以看到他们都实现了接口`Observer`，也就是说他们都是观察者，这是为了实现更换皮肤和字体的功能，采取的**观察者模式**



#### 3.2 菜单栏

`MainWindow`需要设置菜单，菜单栏`MyMenuBar`类，继承自`JMenuBar`，里面包含：

* 菜单`MyMenu`类继承自`JMenu`，其中又包含：
* 菜单项`MyMenuItem`类继承自`JMenuItem`。

##### 3.2.1 菜单栏MyMenuBar

在`MyMenuBar`的构造函数里，`add`添加菜单和菜单项。这里主要介绍为了实现自定义皮肤和界面字体的功能，在`MyMenuBar`中包含字段：

* `skin`皮肤类型和`fontSize`界面字体大小。
* `private ArrayList<Observer> observers`观察者

将其作为**被观察对象**，实现接口`Subject`，当`skin`或者`fontSize`发生变化时，就`notifyObservers`通知注册`observers`中的成员改变字体或者背景皮肤。

接口`Subject`的抽象方法有：

* `void attach(Observer)`添加新的观察者
* `void detach(Observer)`删除观察者
* `void notifySkinObservers(String)`通知观察者换皮肤
* `void notifyFontObservers(Font)`通知观察者换字体

##### 3.2.2 菜单和菜单项

实现了`Observer`接口，也就是抽象方法`updateSkin`改变皮肤和`updateFont`改变字体，收到通知后做出相应改变。

整体菜单栏的UML类图如下：

<img src=".\reportImage\menuUML.png" alt="shapeUML" style="zoom: 80%;" />



#### 3.3 Operation 命令执行者

为了实现多步撤销，采用**命令模式**，将命令的发出者和命令的执行者解耦，`Operation`类就是命令执行者。

由于所有绘图操作都要在`drawPanel`上操作，为了避免`drawPanel`类过于复杂，单独创建了该类放在了`Widget`包内，内部设有字段`MyDrawPanel drawPanel`。

此类是实际执行各种操作的类，内部方法有：

* `Set<AbstractShape> paste(MouseEvent, Set<AbstractShape>)`通过鼠标进行粘贴操作
* `void delete(Set<AbstractShape>)`删除图元
* `void AbstractShape add(String, MouseEvent)`创建新图元
* `void changeSize(int, int, Set<AbstractShape>)`改变图元大小
* `void dragSize(int, int, Set<AbstractShape>)`拖拽改变图元大小

等等。

UML类图如下：

<img src=".\reportImage\operationUML.png" alt="shapeUML" />



### 4. Command包

由于采用命令模式，经过分析本项目涉及的命令有5类：

* `PasteCommand`粘贴命令
* `CreateCommand`创建命令
* `ChangeSizeCommand`改变图元大小命令
* `MoveCommand`移动图元命令
* `DeleteCommand`删除图元命令

均继承自接口`Command`，实现了抽象方法：

* `void execute(MouseEvent e)`需要鼠标信息的执行
* `void execute()`直接执行
* `void undo()`撤销

UML类图如下：

<img src=".\reportImage\commandUML.png" alt="shapeUML" />

## 三、实现方案

### 1. 设计模式

#### 1.1 单例模式

程序只有一个主窗口`mainWindow`，那么这就是个单例，私有化`MainWindow`的构造函数，使用**双检锁**实现：

```java
public class MainWindow extends JFrame {
    private static volatile MainWindow instance = null; // 单例
    //
    //...
    //  
    // 双检锁 懒汉式获取单例的外部接口
    public static MainWindow getInstance () {
        if (instance == null) {
            synchronized (MainWindow.class) {
                if (instance == null) {
                    instance = new MainWindow();
                    drawPanel.mainWindow = instance;
                }
            }
        }
        return instance;
    }
}
```

#### 1.2 原型模式

之前已经介绍过，`AbstractShape`抽象图形类需要深拷贝，因此继承了`Serializable`接口，深拷贝方法如下：

```java
//使用序列化技术实现深拷贝
public AbstractShape deepClone() throws IOException, ClassNotFoundException, OptionalDataException {
    //将对象写入流中
    ByteArrayOutputStream bao = new ByteArrayOutputStream();
    ObjectOutputStream oss = new ObjectOutputStream(bao);
    oss.writeObject(this);
    //将对象从流中取出
    ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bis);
    return (AbstractShape) ois.readObject();
}
```

#### 1.3 命令模式

##### 1.3.1 命令的创建和执行

为了实现多步撤销的功能，在画板`drawPanel`上进行操作时发出的是一个个命令，并且记录命令到列表`ArrayList<Command> undoCmds`中，如下面代码所示：

```java
public void mouseClicked(MouseEvent e) {
	// ...
    if (e.getButton() == MouseEvent.BUTTON1) { // 点击鼠标左键
        String currentMode = MainWindow.getCurrentMode();
        Color penColor = MainWindow.getPenColor();
        if (currentMode.equals("null")) {
            System.out.println("none chosen!");
        } else if (currentMode.equals("copy")) { // 复制图元
            PasteCommand cmd = new PasteCommand(operation, chosenShapes);
            cmd.execute(e);
            undoCmds.add(cmd);
            System.out.println("undocmd size: " + undoCmds.size());
            repaint();
        } else { // 创建图元
            CreateCommand cmd = new CreateCommand(operation, chosenShapes);
            cmd.execute(currentMode, e);
            undoCmds.add(cmd);
            repaint();
        }
        // ...
    }
    // ...
}
```

创建的命令`cmd`调用`execute`方法，在命令内部有对`Operation`类的引用，也就是命令的执行者。

##### 1.3.2 命令的撤销

在撤回按钮上添加点击事件，点击后取出`undoCmds`中最后一条命令，执行其`undo`操作，然后从命令列表中删去该命令，代码如下：

```java
toolBar.toolButtons[8].addActionListener(e -> {
    ArrayList<Command> undoCmds = drawPanel.getUndoCmds();
    if(undoCmds.size() > 0) {
        Command cmd = undoCmds.get(undoCmds.size() - 1);
        cmd.undo();
        undoCmds.remove(cmd);
        System.out.println("undocmd size: " + drawPanel.getUndoCmds().size());
    }
    repaint();
});
```

#### 1.4 观察者模式

皮肤的设置我的实现就是换背景颜色`setBackGround(Color)`。`MyMenuBar`菜单栏作为被观察对象，被其`attach`的观察者有：

* 所有菜单`MyMenu`和菜单项`MyMenuItem`（构造函数中注册

* 取色板`colorPanel`，状态栏`statusBar`和工具栏`toolBar`（由`mainWindow`给`myMenu`注册的

点击某些菜单项则通知观察者改变皮肤或者字体，如下代码所示：

```java
Spring.addActionListener(e -> {
    skin = "spring";
    notifySkinObservers(skin);
});
YaHei.addActionListener(e -> {
    Font font = new Font("微软雅黑", Font.PLAIN, getFontSize());
    notifyFontObservers(font);
});
```

### 2. 文件存储格式

#### 2.1 保存图片文件

要以某种格式保存用户绘制的图，起始就是保存`drawPanel`上的`ArrayList<AbstractShape> shapes`列表，这里面包含了所有图形的全部信息。

因此只需要把这个列表写到文件里就好了，关键代码如下：

```java
try {
    //根据要保存的文件创建对象输出流
    FileOutputStream fis = new FileOutputStream(file);
    ObjectOutputStream oos = new ObjectOutputStream(fis);
    //将容器里面所绘制的图形利用对象流全部写入选中的文件中
    oos.writeObject(MainWindow.getDrawPanel().getShapes());
    JOptionPane.showMessageDialog(null, "保存成功！");
    oos.close();
} catch (Exception e1) {
    e1.printStackTrace();
}
```

#### 2.2 加载图片文件

由于保存的文件就是`AbstractShape`的列表，因此加载时只需要读取文件然后设置成当前画布的`shapes`列表即可，关键代码如下：

```java
try {
    // ...
    // 选中了相应的文件，则选中的文件创建对象输入流
    FileInputStream fis = new FileInputStream(file);
    ObjectInputStream ois = new ObjectInputStream(fis);
    // 将读出来的对象转换成父类对象的容器进行接收
    MainWindow.getDrawPanel().setShapes((ArrayList<AbstractShape>) ois.readObject());
    MainWindow.getDrawPanel().setChooseMore(false);
    ois.close();
    MainWindow.getDrawPanel().repaint();

} catch (Exception e1) {
    e1.printStackTrace();
}
```



## 四、实现功能介绍

实现了基础功能和拓展功能的全部需求，并且额外实现了：

* 支持拖拽移动图形，包括组合图形的移动；
* 支持图元删除，包括组合图形的删除；
* 可以自定义设置画笔颜色；
* 支持导出图片。

### 界面展示

<img src=".\reportImage\GUI.png" alt="image-20220527015003642" style="zoom: 50%;" />

### 功能演示

* 绘制图形时，首先在工具栏选中需要的图形然后**单击鼠标**即可绘制

  > 注意创建图元时是**点击**，**不要拖拽**，想要调整大小创建后再调整

* 添加文字描述时点击"T"按钮，然后点击想要描述的位置，会**弹出对话框**，输入内容即可

* 组合图形时**按下ctrl键**即可选中多个图形

* 复制图形时首先选中一个或多个图形，然后点击复制按钮，再点击**想要粘贴的位置**即可

  > 支持快捷键Ctrl+C和Ctrl+V操作，如果Ctrl+V粘贴的画默认粘贴位置在原图形的右下角，和ppt类似

演示如下：

<img src=".\reportImage\basicshow.gif" alt="shapeUML" style="zoom: 50%;" />



* 可右击图元弹出**对话框**调整图元大小，也可以选中图元后**拖拽右下角**调整图元大小，**均支持组合图形**

演示如下：

<img src=".\reportImage\changesize.gif" alt="shapeUML" style="zoom:50%;" />



* 支持拖拽移动（包括组合图形），撤销**多步**，操作后点击**撤回按钮**或者**Ctrl+Z**快捷键即可；支持自定义界面皮肤和界面字体，在菜单栏点击即可，演示如下：

<img src=".\reportImage\show3.gif" alt="shapeUML" style="zoom:50%;" />



* 支持保存可存储文件，点击菜单栏保存或者Ctrl+S快捷键；同样支持加载可存储文件，点击菜单栏打开或者Ctrl+O快捷键；支持导出图片，点击菜单栏导出图片或者Ctrl+I快捷键。

  > 可存储文件的默认格式为`.my`后缀

演示如下：

<img src=".\reportImage\fileshow_.gif" alt="shapeUML" style="zoom:50%;" />

* 支持图元（包括组合图元）删除，选中图元后点击删除按钮或者Ctrl+D快捷键；

