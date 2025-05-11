
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


