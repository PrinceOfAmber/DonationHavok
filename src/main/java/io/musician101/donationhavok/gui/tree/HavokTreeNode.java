package io.musician101.donationhavok.gui.tree;

import com.google.gson.JsonElement;
import java.util.Collections;
import java.util.Enumeration;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public abstract class HavokTreeNode<J extends JsonElement> implements MutableTreeNode {

    @Nullable
    private String key;
    private MutableTreeNode parent;

    HavokTreeNode(@Nullable String key) {
        this.key = key;
    }

    @Override
    public Enumeration children() {
        return Collections.emptyEnumeration();
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public int getIndex(TreeNode node) {
        return -1;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    public void setKey(@Nullable String key) {
        this.key = key;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        this.parent = newParent;
    }

    @Override
    public void insert(MutableTreeNode child, int index) {

    }

    @Override
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void remove(MutableTreeNode node) {

    }

    @Override
    public void removeFromParent() {
        if (parent != null) {
            parent.remove(this);
        }
    }

    @Nonnull
    protected abstract J serialize();

    @Override
    public void setUserObject(Object object) {

    }
}
