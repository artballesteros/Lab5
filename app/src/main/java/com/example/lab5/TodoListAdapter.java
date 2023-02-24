package com.example.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {
    private List<TodoListItem> todoItems = Collections.emptyList();
    private Consumer<TodoListItem> onCheckBoxedClicked;
    private BiConsumer<TodoListItem, String> onTextEditedHandler;
    private Consumer<TodoListItem> onDeleteBtnClicked;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final CheckBox checkBox;
        private final TextView deleteBtn;
        private TodoListItem todoItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.todo_item_text);
            this.checkBox = itemView.findViewById(R.id.checkBox);
            this.deleteBtn = itemView.findViewById(R.id.delete_btn);

            this.textView.setOnFocusChangeListener((view, hasFocus) -> {
                if (hasFocus) return;
                onTextEditedHandler.accept(todoItem, textView.getText().toString());
            });

            this.checkBox.setOnClickListener(view -> {
                if (onCheckBoxedClicked == null) return;
                onCheckBoxedClicked.accept(todoItem);
            });

            this.deleteBtn.setOnClickListener(view -> {
                if (onDeleteBtnClicked == null) return;
                onDeleteBtnClicked.accept(todoItem);
            });
        }


        public TodoListItem getTodoItem() {
            return todoItem;
        }

        public void setTodoItem(TodoListItem todoItem) {
            this.todoItem = todoItem;
            this.textView.setText(todoItem.text);
            this.checkBox.setChecked(todoItem.completed);
        }
    }

    public void setOnTextEditedHandler(BiConsumer<TodoListItem, String> onTextEditedHandler) {
        this.onTextEditedHandler = onTextEditedHandler;
    }

    public void setOnCheckBoxedClickedHandler(Consumer<TodoListItem> onCheckBoxedClicked) {
        this.onCheckBoxedClicked = onCheckBoxedClicked;
    }

    public void setOnDeleteBtnClickedHandler(Consumer<TodoListItem> onDeleteBtnClicked) {
        this.onDeleteBtnClicked = onDeleteBtnClicked;
    }

    public void setTodoListItems(List<TodoListItem> newTodoItems) {
        this.todoItems.clear();
        this.todoItems = newTodoItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTodoItem(todoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    @Override
    public long getItemId(int position) {
        return todoItems.get(position).id;
    }
}
