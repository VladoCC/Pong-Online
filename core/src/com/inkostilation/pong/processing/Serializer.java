package com.inkostilation.pong.processing;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.inkostilation.pong.commands.ICommand;

import java.lang.reflect.Type;

public class Serializer {

    private static Gson gson;

    public Serializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Parcel.class, new ParcelDeserializer());
        gson = builder.create();
    }

    public ICommand deserialize(String json) {
        Parcel parcel = gson.fromJson(json, Parcel.class);
        Class<? extends ICommand> commandClass = null;
        try {
            commandClass = (Class<? extends ICommand>) Class.forName(parcel.commandClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return commandClass.cast(parcel.command);
    }

    public String serialize(ICommand command) {
        return gson.toJson(new Parcel(command), Parcel.class);
    }

    private final class Parcel {
        String commandClass;
        ICommand command;

        public Parcel(ICommand command) {
            this.command = command;
            commandClass = command.getClass().getCanonicalName();
        }
    }

    private final class ParcelDeserializer implements JsonDeserializer<Parcel> {

        @Override
        public Parcel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final JsonPrimitive commandClass = (JsonPrimitive) jsonObject.get("commandClass");
            final String className = commandClass.getAsString();
            final JsonElement commandJson = jsonObject.get("command");
            ICommand command;
            try {
                command = context.deserialize(commandJson, Class.forName(className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                command = null;
            }
            return new Parcel(command);
        }
    }
}
