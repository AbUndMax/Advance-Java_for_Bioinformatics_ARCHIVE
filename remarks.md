
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



