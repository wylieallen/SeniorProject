package guiframework.gui3d.util;

import graphicslib3D.Matrix3D;

import java.util.Stack;

public class MatrixStack
{
    private static Stack<Matrix3D> stack = new Stack<>();

    public static void initialize(Matrix3D matrix) { stack.push(matrix); }

    public static void concat(Matrix3D matrix) { stack.peek().concatenate(matrix); }

    public static void push() { stack.push(new Matrix3D(stack.peek().getValues())); }

    public static Matrix3D peek() { return stack.peek(); }
    public static Matrix3D pop() { return stack.pop(); }
}
