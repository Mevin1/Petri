package me.b3nw.dev.Packets.Encode;

import me.b3nw.dev.Session.Blobs.Blob;
import me.b3nw.dev.Session.Ticks.Death;
import me.b3nw.dev.Session.Ticks.Tick;

import java.util.ArrayList;
import java.util.Iterator;

public class Tick16 extends Encode {

    private final Tick tick;
    private ArrayList<Blob> destroyList;

    public Tick16(Tick tick) {
        super(ID.TICK.id);
        this.tick = tick;
    }

    @Override
    protected boolean Encoder() {

        data.writeShort(tick.getDeaths().length);

        for (int i = 0; i < tick.getDeaths().length; i++) {
            Death death = tick.getDeaths()[i];

            data.writeInt(death.getEater().getId());
            data.writeInt(death.getEaten().getId());
        }

        for (int i = 0; i < tick.getBlobs().length; i++) {
            Blob blob = tick.getBlobs()[i];

            data.writeInt(blob.getId()); //Blob id

            data.writeShort((int) Math.round(blob.getX())); //x loc rounded to int
            data.writeShort((int) Math.round(blob.getY())); //y loc rounded to int
            data.writeShort((int) Math.round(blob.getSize())); //size rounded to int

            data.writeByte(blob.getR()); //Red value
            data.writeByte(blob.getG()); //Green value
            data.writeByte(blob.getB()); //Blue value

            if (blob.isVirus()) {
                data.writeByte(1); //Virus true
            } else {
                data.writeByte(0); //Virus false
            }

            if (!tick.getOwner().knowsBlob(blob)) { //If the blob is unknown to tick owner send name
                String name = blob.getName();

                for (int i2 = 0; i2 < name.length(); i2++) {
                    data.writeChar(name.charAt(i2)); //Write each char of username
                }
            }

            data.writeChar(0); //EOF for blob name
        }

        data.writeInt(0); //EOF for blob array

        calcDestroyList();

        data.writeInt(destroyList.size()); //Destroy count

        Iterator<Blob> blobIt = destroyList.iterator();

        while (blobIt.hasNext()) {
            data.writeInt(blobIt.next().getId()); //Id's to destroy
        }

        return true;
    }

    private void calcDestroyList() {
        for (int i = 0; i < tick.getDeaths().length; i++) {
            destroyList.add(tick.getDeaths()[i].getEaten());
        }

        Blob[] compared = tick.getOwner().calcBlobDeaths(tick.getBlobs());

        for (int i = 0; i < compared.length; i++) {
            destroyList.add(compared[i]);
        }
    }

}
