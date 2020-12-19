package com.inkostilation.pong.desktop.display;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.inkostilation.pong.desktop.controls.InputSystem;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.util.adapter.ArrayListAdapter;
import com.kotcrab.vis.ui.util.adapter.ListAdapter;
import com.kotcrab.vis.ui.util.adapter.SimpleListAdapter;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.util.ArrayList;
import java.util.Arrays;

public class LobbyScreen implements Screen {

    private Stage view;
    private InputMultiplexer multiplexer = new InputMultiplexer();
    private ArrayList<String[]> list = new ArrayList<>();

    @Override
    public void show() {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }

        view = new Stage();
        Gdx.input.setInputProcessor(view);


        list.add(new String[]{"test", "second", "123"});
        list.add(new String[]{"1111", "----", "\\./"});

        ArrayListAdapter<String[], HorizontalGroup> test = new ArrayListAdapter<String[], HorizontalGroup>(list) {
            @Override
            protected HorizontalGroup createView(String[] item) {
                HorizontalGroup table = new HorizontalGroup();
                for (String i : item) {
                    table.addActor(new VisLabel(i));
                }
                return table;
            }
        };

        VisTable table = new VisTable();
        view.addActor(table);
        table.setBounds(0,0, 1280, 720);
        table.center().top().add(new VisLabel("Lobby"));
        table.row();

        ListView<String[]> view = new ListView<>(test);
        view.setItemClickListener(new ListView.ItemClickListener<String[]>() {
            @Override
            public void clicked(String[] item) {
                System.out.println(Arrays.toString(item));
            }
        });

        table.add(view.getMainTable()).fill();

        table.row().bottom();
        table.add(new VisTextButton("Connect")).bottom().padRight(200);
        table.add(new VisTextButton("New"));
        //table.pack();
    }

    @Override
    public void render(float delta) {
        view.act();
        view.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
