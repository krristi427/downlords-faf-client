package com.faforever.client.chat;

import com.faforever.client.theme.UiService;
import javafx.scene.control.ListCell;

import java.util.Objects;
import java.util.Optional;

public class ChatUserListCell extends ListCell<CategoryOrChatUserListItem> {

  private final ChatUserItemController chatUserItemController;
  private final ChatUserItemCategoryController chatUserCategoryController;
  private Optional<Runnable> onSocialStatusUpdatedListener;
  private Object oldItem;

  public ChatUserListCell(UiService uiService) {
    chatUserItemController = uiService.loadFxml("theme/chat/chat_user_item.fxml");
    chatUserCategoryController = uiService.loadFxml("theme/chat/chat_user_category.fxml");

    setText(null);
  }

  // TODO check whether or not this is meant to be used. Fix accordingly.
  public void setOnSocialStatusUpdatedListener(Runnable onSocialStatusUpdatedListener) {
    this.onSocialStatusUpdatedListener = Optional.ofNullable(onSocialStatusUpdatedListener);
  }

  @Override
  protected void updateItem(CategoryOrChatUserListItem item, boolean empty) {
    if (Objects.equals(item, oldItem)) {
      return;
    }
    oldItem = item;

    super.updateItem(item, empty);

    if (item == null || empty) {
      chatUserItemController.setChatUser(null);
      chatUserCategoryController.setChatUserCategory(null);
      setGraphic(null);
      return;
    }

    if (item.getUser() != null) {
      chatUserCategoryController.setChatUserCategory(null);
      chatUserItemController.setChatUser(item.getUser());
      chatUserItemController.setOnSocialStatusUpdatedListener(onSocialStatusUpdatedListener);
      setGraphic(chatUserItemController.getRoot());
    } else {
      chatUserItemController.setChatUser(null);
      chatUserCategoryController.setChatUserCategory(item.getCategory());
      setGraphic(chatUserCategoryController.getRoot());
    }
  }
}