package net.cwserver.netty.data;

import io.netty.buffer.ByteBuf;
import sun.security.util.BitArray;

public class EntityData implements BaseData {

    long id;
    byte[] bitmask;

    long posX;
    long posY;
    long posZ;

    float roll;
    float pitch;
    float yaw;

    Vector3 velocity;

    Vector3 accel;

    Vector3 extraVel;

    float lookPitch;
    long physicsFlags; //Uint
    byte speedFlags;
    long entityType; //Uint
    byte currentMode;
    long lastShootTime; //Uint
    long hitCounter; //Uint
    long lastHitTime; //Uint
    Appearance app;
    byte flags1;
    byte flags2;
    long rollTime; //Uint
    int stunTime;
    long slowedTime; //Uint
    long makeBlueTime; //Uint
    long speedUpTime; //Uint
    float slowPatchTime;
    byte classType;
    byte specialization;
    float chargedMP;

    Vector3 rayHit;

    float HP;
    float MP;

    float blockPower;
    float maxHPMultiplier;
    float shootSpeed;
    float damageMultiplier;
    float armorMultiplier;
    float resistanceMultiplier;
    long level;  //Uint
    long currentXP; //Uint
    Item itemData;
    Item[] equipment;

    long iceBlockFour; //Uint
    long[] skills;
    String name;

    long na1; //Uint
    long na2; // |
    byte na3;
    long na4;
    long na5;
    long nu1;
    long nu2;
    long nu3;
    long nu4;
    long nu5;
    long nu6;
    byte nu7;
    byte nu8;
    long parentOwner;
    long nu9;
    long nu10;
    long nu11;
    long nu12;
    long nu13;
    long nu14;
    long nu15;
    long nu16;
    long nu17;
    long nu18;
    long nu20;
    long nu21;
    long nu22;
    byte nu19;

    public EntityData() {
        bitmask = new byte[8];
        velocity = new Vector3();
        accel = new Vector3();
        extraVel = new Vector3();
        rayHit = new Vector3();
        app = new Appearance();
        itemData = new Item();
        equipment = new Item[13];
        for(int i = 0; i < 13; i++)
            equipment[i] = new Item();

        skills = new long[11];

    }

    @Override
    public void decode(ByteBuf buf) {
        bitmask = buf.readBytes(8).array();
        BitArray bitArray = new BitArray(8*2, bitmask); //Size in bits, byte[]

        if(bitArray.get(0)) {
            posX = buf.readLong();
            posY = buf.readLong();
            posZ = buf.readLong();
        }
        if(bitArray.get(1)) {
            roll = buf.readFloat();
            pitch = buf.readFloat();
            yaw = buf.readFloat();
        }
        if(bitArray.get(2)) {
            velocity.decode(buf);
        }
        if(bitArray.get(3)) {
            accel.decode(buf);
        }
        if(bitArray.get(4)) {
            extraVel.decode(buf);
        }
        if(bitArray.get(5)) {
            lookPitch = buf.readFloat();
        }
        if(bitArray.get(6)) {
            physicsFlags = buf.readUnsignedInt();
        }
        if(bitArray.get(7)) {
            speedFlags = buf.readByte();
        }
        if(bitArray.get(8)) {
            entityType = buf.readUnsignedInt();
        }
        if(bitArray.get(9)) {
            currentMode = buf.readByte();
        }
        if (bitArray.get(10)) {
            lastShootTime = buf.readUnsignedInt();
        }
        if(bitArray.get(11)) {
            hitCounter = buf.readUnsignedInt();
        }
        if(bitArray.get(12)) {
            lastHitTime = buf.readUnsignedInt();
        }
        if(bitArray.get(13)) {
            app.decode(buf);
        }
        if(bitArray.get(14)) {
            flags1 = buf.readByte();
            flags2 = buf.readByte();
        }
        if(bitArray.get(15))  {
            rollTime = buf.readUnsignedInt();
        }
        if(bitArray.get(16)) {
            stunTime = buf.readInt();
        }
        if(bitArray.get(17)) {
            slowedTime = buf.readUnsignedInt();
        }
        if(bitArray.get(18)) {
            makeBlueTime = buf.readUnsignedInt();
        }
        if(bitArray.get(19)) {
            speedUpTime = buf.readUnsignedInt();
        }
        if(bitArray.get(20)) {
            slowPatchTime = buf.readFloat();
        }
        if(bitArray.get(21)) {
            classType = buf.readByte();
        }
        if(bitArray.get(22)) {
            specialization = buf.readByte();
        }
        if(bitArray.get(23)) {
            chargedMP = buf.readFloat();
        }
        if(bitArray.get(24)) {
            nu1 = buf.readUnsignedInt();
            nu2 = buf.readUnsignedInt();
            nu3 = buf.readUnsignedInt();
        }
        if(bitArray.get(25)) {
            nu4 = buf.readUnsignedInt();
            nu5 = buf.readUnsignedInt();
            nu6 = buf.readUnsignedInt();
        }
        if(bitArray.get(26)) {
            rayHit.decode(buf);
        }
        if(bitArray.get(27)) {
            HP = buf.readFloat();
        }
        if(bitArray.get(28)) {
            MP = buf.readFloat();
        }
        if(bitArray.get(29)) {
            blockPower = buf.readFloat();
        }
        if(bitArray.get(30)) {
            maxHPMultiplier = buf.readFloat();
            shootSpeed = buf.readFloat();
            damageMultiplier = buf.readFloat();
            armorMultiplier = buf.readFloat();
            resistanceMultiplier = buf.readFloat();
        }
        if(bitArray.get(31)) {
            nu7 = buf.readByte();
        }
        if(bitArray.get(32)) {
            nu8 = buf.readByte();
        }
        if(bitArray.get(33)) {
            level = buf.readUnsignedInt();
        }
        if (bitArray.get(34)) {
            currentXP = buf.readUnsignedInt();
        }
        if(bitArray.get(35)) {
            parentOwner = buf.readLong();
        }
        if(bitArray.get(36)) {
            nu1 = buf.readUnsignedInt();
            nu2 = buf.readUnsignedInt();
        }
        if(bitArray.get(37)) {
            nu3 = buf.readByte();
        }

    }

    @Override
    public void encode(ByteBuf buf) {

    }
}