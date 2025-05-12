package org.vaddin.demo.common.base;

public interface Presenter<T extends View> {

    T getView();

    void setView(T view);
}