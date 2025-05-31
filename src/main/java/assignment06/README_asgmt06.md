# Task 2: Explanation of Mouse Rotation Functionality

---

# Task 5: Explanation how rotate group around its center

group.getChildren().addListener((IvalidationListener) e -> method())

- Adds an IvalidationListener to the ObservableList of children of group. Whenever the list is changed, it becomes 'invalidated' because it is out-of-date (sort of). This notifies the InvalidationListener and calls the method.

When we rotate the group, we want it to rotate around its center point. But the rotation always rotates around the origin of the coordinate system, so the group's center point needs to be moved to the origin. Thus we must know the group's center point. This is obtained by getting the bounds in all directions of the coordinate system and taking their center:

double X = (bounds.getMinX() + bounds.getMaxX()) / 2; and so forth for Y and Z.

If X = 0, everything is fine. If X = a != 0, a movement must be applied to X which is of equal magnitude and opposite direction of a (so -a). Thus we apply the new Translate (-X -Y -Z) so that X = Y = Z = 0.
