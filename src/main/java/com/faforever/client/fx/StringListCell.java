package com.faforever.client.fx;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import lombok.Setter;

import java.util.Objects;
import java.util.function.Function;

public class StringListCell<T> extends ListCell<T> {

  private final Function<T, Node> graphicFunction;
  private final Function<T, String> function;
  private final Pos alignment;
  private final String[] cssClasses;
  @Setter
  private Function<T, Tooltip> tooltipFunction;

  public StringListCell(Function<T, String> function) {
    this(function, null);
  }

  public StringListCell(Function<T, String> function, Function<T, Node> graphicFunction) {
    this(function, graphicFunction, Pos.CENTER_LEFT);
  }

  public StringListCell(Function<T, String> function, Function<T, Node> graphicFunction, Pos alignment, String... cssClasses) {
    this.function = function;
    this.alignment = alignment;
    this.cssClasses = cssClasses;
    this.graphicFunction = graphicFunction;
  }

  @Override
  protected void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);

    Platform.runLater(() -> {
      if (empty || item == null) {
        setText(null);
        setGraphic(null);
        setTooltip(null);
      } else {
        if (graphicFunction != null) {
          setGraphic(graphicFunction.apply(item));
        }
        setText(Objects.toString(function.apply(item), ""));
        if (tooltipFunction != null) {
          setTooltip(tooltipFunction.apply(item));
        } else {
          setTooltip(null);
        }
        setAlignment(alignment);
        getStyleClass().addAll(cssClasses);
      }
    });
  }
}
