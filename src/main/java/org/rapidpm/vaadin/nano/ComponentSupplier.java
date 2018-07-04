package org.rapidpm.vaadin.nano;

import com.vaadin.ui.Component;

import java.util.function.Supplier;

@FunctionalInterface
public interface ComponentSupplier extends Supplier<Component> { }
