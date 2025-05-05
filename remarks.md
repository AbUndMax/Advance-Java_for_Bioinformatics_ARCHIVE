
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
