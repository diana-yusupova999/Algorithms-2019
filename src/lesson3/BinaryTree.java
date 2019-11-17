package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private final Stack<Node<T>> stack;
        private Node<T> prev;

        private BinaryTreeIterator() {
            stack = new Stack<>();
            prev = null;

            Node<T> current = root;
            if (current != null) {
                stack.add(current);
                while (current.left != null) {
                    current = current.left;
                    stack.add(current);
                }
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         *
         * O(1) T(n)
         */
        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         *
         * O(1) T(n)
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            final Node<T> res = stack.pop();
            if (res.right != null) {
                Node<T> elem = res.right;
                stack.add(elem);
                while (elem.left != null) {
                    stack.add(elem.left);
                    elem = elem.left;
                }
            }
            prev = res;
            return (T) res.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            if (prev == null) {
                throw new NoSuchElementException();
            }

            final Node<T> removableElem = prev;

            if (removableElem.left == null && removableElem.right == null) {
                swapWithDelete(removableElem, null);
            } else if (removableElem.left != null && removableElem.right != null) {
                Node<T> current = removableElem.right;
                Node<T> parent = removableElem;
                while (current.left != null) {
                    parent = current;
                    current = current.left;
                }
                parent.left = current.right;
                removableElem.value = current.value;
            } else if (removableElem.left == null) {
                swapWithDelete(removableElem, removableElem.right);
            } else {
                swapWithDelete(removableElem, removableElem.left);
            }
            size--;
        }

        private void swapWithDelete(Node<T> child, Node<T> aNewChild) {
            if (!stack.empty()) {
                Node<T> parent = stack.peek();
                if (parent.right == child) {
                    parent.right = aNewChild;
                } else {
                    parent.left = aNewChild;
                }
            } else {
                root = aNewChild;
            }
        }

    }


    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     *
     * O(n) T(n)
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        final SortedSet<T> binaryTree = new BinaryTree<>();
        for (T next : this) {
            if (next.compareTo(toElement) < 0) {
                binaryTree.add(next);
            } else {
                break;
            }
        }
        return binaryTree;
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     * O(n) T(n)
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        final SortedSet<T> binaryTree = new BinaryTree<>();

        for (T next : this) {
            if (next.compareTo(fromElement) >= 0) {
                binaryTree.add(next);
            }
        }

        return binaryTree;
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
