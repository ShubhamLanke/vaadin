package org.vaddin.demo.common.base;

public interface View<T extends Presenter> {

    T getPresenter();
}
