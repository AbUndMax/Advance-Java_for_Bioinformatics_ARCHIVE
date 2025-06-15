
## 📘 Assignment 01 – Feedback

I’ve updated your `pom.xml` to use the latest version, as the original one was outdated.

Regarding `module-info.java`:
You're using Java modules, but the **ArgsParser** library you included is not a named module (i.e., it does **not contain a `module-info.class`**). Java’s module system **does not allow automatic access from named modules to unnamed modules**, which is why this approach breaks.

I tried cloning the library to add a `module-info.java`, but to use it properly, it would need to be published to a Maven repository — which I avoided since this is your project. You're welcome to try this if you prefer.

That said, it's usually better to use **existing and well-supported packages already available via Maven Central**. This avoids issues like GitHub token setup or custom settings (`settings.xml`) that others — like Daniel — might not want to configure when reviewing your project.

---

### ✅ **Task 2**

* **Design:** OK
* **Functionality:** OK
* **Points:** ✅ **2/2**

---

### ✅ **Task 3**

* It would be better to move the print logic into the `ShowRelationsTree` class for better structure and readability.

* Your code is somewhat redundant — the print logic is split across multiple classes, and some of them could be merged or simplified for clarity.

* You're using `contains()` for keyword matching, which is a bit loose — it may match substrings unintentionally (e.g., "art" in "artery"). If you aim for **exact or robust matches**, consider token-based matching or regular expressions.

* **Points:** ✅ **8/8**

---

### ✅ **Total: 10/10**


---


## 📘 Assignment 02 – Feedback

**Task 1**

* **Implementation:** The implementation is fine. However, there are some classes that are not being used — consider commenting them out or deleting them if not needed. I've added the `module-info.java` file. Going forward, please include it, as we will not be using any libraries that are not available on Maven.
* **Functionality:** OK
* **Points:** ✅ **4/4**

**Task 2**

* **Design:** The design looks good.
* **Points:** ✅ **2/2**

**Task 3**

* **Implementation:** The implementation is fine. One small point: I'm not sure if we need to hardcode the file path in `TreeLoader`, since it should work directly via `TreeLoader.load*()` from `AnatomyDataExplorer`. But it's not a big issue.
* **Functionality:** OK
* **Points:** ✅ **4/4**

**Task 4**

* **Design:** The design is fine and all required elements are in the correct order.
* **Functionality:** OK
* **Points:** ✅ **3/3**

**Task 5**

* **Design:** The design is fine and all required elements are in the correct order.
* **Functionality:** OK

    * ⚠️ *Issue:* When the **Expand** button is clicked, the previously selected node in the `TreeView` remains highlighted. This can be confusing for users, as the selection may not correspond to the newly visible nodes.
    * 🛠️ *How to fix it:* Add the following line before calling the recursive expansion method to clear the current selection:

      ```java
      treeView.getSelectionModel().clearSelection();
      ```

      Since `treeView` is currently a local variable inside `start()`, you can:

        1. Make `treeView` an instance variable (e.g. declare `private TreeView<ANode> treeView;` at the top).
        2. Assign it in `start()` and access it inside `expandTreeView()` and `collapseTreeView()`, or
        3. If keeping it local, move the selection clearing directly inside the button action like this:

      ```java
      expandButton.setOnAction(e -> {
          treeView.getSelectionModel().clearSelection();  // Clear selection before expanding
          expandTreeView(rootItem);
      });
  
      collapseButton.setOnAction(e -> {
          treeView.getSelectionModel().clearSelection();  // Clear selection before collapsing
          collapseTreeView(rootItem);
      });
      ```

      This ensures a cleaner and more intuitive user experience.

* **Points:** ✅ **2/2**

---

### ✅ **Total: 15/15**

---

## 📘 Assignment 03 – Feedback

---

### **Task 1 – Design**

All required components were implemented.
However, in your FXML:

```xml
<bottom>
   <HBox prefHeight="20.0" BorderPane.alignment="CENTER" />
</bottom>
```

This `<HBox>` is empty, but your diagram suggests it contains content. While it's not functionally incorrect, it may confuse readers or suggest missing content.

Right now, it likely serves one of the following roles:

✅ **1. Acts as padding/spacing**
Adds a 20-pixel height at the bottom for visual balance.

✅ **2. Placeholder for future content**
You may later use it to display:

* A **status bar**
* **Debug info**, or
* A **footer label**

I'm just pointing this out — **no points deducted**.
also is it hbox or herobox?
**Points:** 3/3

---

### **Task 2 – Implementation**

Your implementation is correct and meets expectations.

**Points:** 1/1

---

### **Task 3 – Implementation & Functionality**

Correct implementation. The functionality works as expected, and your response to the question is also accurate.

**Points:** 2/2

---

### **Task 4 – Implementation & Functionality**

* The implementation works correctly and performs all required tasks.
* However, the shortcut behavior is tailored more toward macOS — it's unclear how it will behave on Windows/Linux. JavaFX provides ways to handle this cross-platform.
* There’s also a **logic issue** with "Expand All" and "Collapse All":

  > If you select something, the word cloud updates — but when you expand or collapse, the selection **visually disappears** from the TreeView while the word cloud **still shows data**. This causes a **sync issue**.

#### ✅ Fix applied:

To resolve this, I modified the code so that when a user expands or collapses:

* **Selection is cleared**
* **Word cloud is also cleared**

This ensures UI and data remain in sync.

#### ✏️ Step 1: Change method signatures

```java
// OLD
private void expandTreeView(TreeView<ANode> treeView);
private void collapseTreeView(TreeView<ANode> treeView);

// NEW
private void expandTreeView(WindowController controller);
private void collapseTreeView(WindowController controller);
```

---

#### ✏️ Step 2: Update their contents

```java
private void expandTreeView(WindowController controller) {
    TreeView<ANode> treeView = controller.getTreeView();
    var selectedNodes = treeView.getSelectionModel().getSelectedItems();

    if (selectedNodes.isEmpty()) {
        expandAllBelowGivenNode(treeView.getRoot());
    } else {
        for (TreeItem<ANode> node : selectedNodes) {
            expandAllBelowGivenNode(node);
        }
    }

    treeView.getSelectionModel().clearSelection();     // Clear selection
    controller.getFlowPane().getChildren().clear();    // Clear word cloud
}

private void collapseTreeView(WindowController controller) {
    TreeView<ANode> treeView = controller.getTreeView();
    var selectedNodes = treeView.getSelectionModel().getSelectedItems();

    if (selectedNodes.isEmpty()) {
        collapseAllNodesUptToGivenNode(treeView.getRoot());
    } else {
        for (TreeItem<ANode> node : selectedNodes) {
            collapseAllNodesUptToGivenNode(node);
        }
    }

    treeView.getSelectionModel().clearSelection();     // Clear selection
    controller.getFlowPane().getChildren().clear();    // Clear word cloud
}
```

---

#### ✏️ Step 3: Update handler calls

```java
// OLD
controller.getMenuItemExpandAll().setOnAction(e -> expandTreeView(controller.getTreeView()));
controller.getMenuItemCollapseAll().setOnAction(e -> collapseTreeView(controller.getTreeView()));
controller.getButtonExpandAll().setOnAction(e -> expandTreeView(controller.getTreeView()));
controller.getButtonCollapseAll().setOnAction(e -> collapseTreeView(controller.getTreeView()));

// NEW
controller.getMenuItemExpandAll().setOnAction(e -> expandTreeView(controller));
controller.getMenuItemCollapseAll().setOnAction(e -> collapseTreeView(controller));
controller.getButtonExpandAll().setOnAction(e -> expandTreeView(controller));
controller.getButtonCollapseAll().setOnAction(e -> collapseTreeView(controller));
```

---

### 🚧 Note

> There is still a potential performance issue:
> When attempting to generate a word cloud for the **entire tree**, your app consumes significant memory and may crash on lower-end machines.
> You may want to look into optimizing how large selections are handled (e.g., lazy layout, limiting words, caching, etc.).
> collapseAll still have some issues.
 


**Points:** 3/3

---

### **Task 5 – Design**

All required menu items are present and correctly implemented.

**Points:** 1/1

---

### ✅ **Total: 10/10**


---

## 📝 **General Feedback**

You're not using Java resources to load your files properly. Even though your files are in the `resources` directory, you're hardcoding paths like:

```
./src/main/resources/assignment04/partof_parts_list_e.txt
```

This should instead be accessed using the resource system:

```java
getClass().getResource("/assignment04/partof_parts_list_e.txt")
```

This will allow your application to load files reliably in both development and packaged environments.

---

## 📝 **Assignment 04**

---

### **Task 1**

#### ✅ Functionality & Design:

Everything is implemented as expected and functions correctly.

**✔️ Points: 2 / 2**

---

### **Task 2**

#### ✅ Functionality:

The task works fine overall.
One small issue: the **root node has no name**, which is acceptable.
However, in some places, **internal nodes are not aligned with their children (leaves)** — they appear slightly shifted.

**✔️ Points: 4 / 4**

---

### **Task 3**

#### ✅ Functionality:

The implementation behaves as expected without any issues.

**✔️ Points: 4 / 4**

---

### **Task 4**

#### ✅ Functionality:

It's nice that you implemented Newick export — it works correctly.
Also great that you added a **filtering feature** — that’s a useful addition.

---
except ANode nothing else belong to that class, all other classes can be part of TreeLoader. 
**✅ Total: 10 / 10**

---

## 📝 **Assignment 05**

---

### **Task 1**

#### ✅ Functionality & Design:

The implementation works as required.
However, the 3D object still doesn't appear **properly centered** in the view.

**✔️ Points: 4 / 4**

---

### **Task 2**

#### ✅ Functionality:

Works as expected — no issues found.

**✔️ Points: 3 / 3**

---

### **Task 3**

#### ✅ Explanation:

The explanation of the `.obj` file format is clear and correct.

**✔️ Points: 1 / 1**

---

### **Task 4**

#### ✅ Functionality:

It works fine overall, but:

* When loading a **new OBJ file**, it **starts from the same position** — you need to reset the view/camera.
* The **head model appears upside down**, indicating a likely issue with axis orientation.
* Also, when loading the skull, then the map, and clicking "Reset", the centering becomes misaligned — your centering logic needs refinement to handle switching between models correctly.

**✔️ Points: 2 / 2**

---
### Important

#### 🔁 **Correction (applies to Assignment 05):**

The class `ObjParser` should be placed in the **`model`** package, not in the `window` package.
It is a **data parsing and logic class**, and therefore belongs in the **model layer**, not the UI/view layer.

Keeping the class in the correct package helps maintain **clean architecture and separation of concerns** (Model–View–Presentor principle).

---


**✅ Total: 10 / 10**




---

## **Assignment 06**

**Note:**
The **"Add Axis" functionality** has an issue — the axis is not properly centered. I tried to debug it, but it seems the fix may require additional coding. If I get more time, I’ll look into it further. Alternatively, we can discuss it during the tutorial session.

---

### **Task 1**

**Implementation:**
Everything has been implemented as required.

**Points:** 1/1

---

### **Task 2**

**Functionality:**
Works as expected.

**Points:** 2/2

---

### **Task 3**

**Functionality:**
Works fine.

**Points:** 2/2

---

### **Task 4**

**Functionality:**
Also works fine.

**Points:** 2/2

---

### **Task 5**

**Functionality:**
Works smoothly. Good job overall. 
**Points:** 3/3

---

### **Total: 10/10**

Great work! The explanation is clear and well written.
