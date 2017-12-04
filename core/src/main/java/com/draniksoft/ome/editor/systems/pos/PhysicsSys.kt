package com.draniksoft.ome.editor.systems.pos

import com.artemis.Aspect
import com.artemis.BaseEntitySystem
import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import com.artemis.annotations.Wire
import com.artemis.utils.IntBag
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.draniksoft.ome.editor.components.pos.PhysC
import com.draniksoft.ome.editor.components.pos.PosSizeC
import com.draniksoft.ome.editor.components.tps.MObjectC
import com.draniksoft.ome.utils.PUtils
import com.draniksoft.ome.utils.struct.Pair


class PhysicsSys : BaseEntitySystem(Aspect.all(PhysC::class.java)) {

    @Wire
    internal var pw: World? = null

    internal var pM: ComponentMapper<PhysC>? = null
    internal var p2M: ComponentMapper<PosSizeC>? = null

    internal var passA = 10
    internal var passS = passA

    override fun initialize() {

        getSubscription().addSubscriptionListener(object : EntitySubscription.SubscriptionListener {
            override fun inserted(entities: IntBag) {

            }

            override fun removed(es: IntBag) {

                for (i in 0..es.size() - 1) {
                    pw!!.destroyBody(pM!!.get(es.get(i)).b)
                }

            }
        })

    }

    override fun processSystem() {

        if (passS > 0) {
            passS--
            return
        }

        pw!!.step(Gdx.graphics.deltaTime, 2, 2)

        passS = passA
    }

    // returns physics pos in map coords
    fun getPhysPos(_e: Int): Pair<Float, Float>? {

        if (!pM!!.has(_e)) return null

        val b = pM!!.get(_e).b

        return Pair.createPair(b.position.x * PUtils.PPM, b.position.y * PUtils.PPM)

    }

    fun setCentricPos(x: Float, y: Float, _e: Int) {
        if (pM!!.has(_e)) {

            pM!!.get(_e).b.setTransform(x / PUtils.PPM, y / PUtils.PPM, pM!!.get(_e).b.angle)

        }
    }

    // positions in the center
    fun setSyncCentricPos(x: Float, y: Float, _e: Int) {

        setCentricPos(x, y, _e)

        if (p2M!!.has(_e)) {
            val c = p2M!!.get(_e)
            c.x = x.toInt() - c.w / 2
            c.y = y.toInt() - c.h / 2
        }


    }

    fun createBodyFromSPos(x: Int, y: Int, _e: Int) {

        val pc = p2M!!.get(_e)

        val c = pM!!.create(_e)

        val bd = BodyDef()
        bd.type = BodyDef.BodyType.KinematicBody
        bd.position.set(x / PUtils.PPM, y / PUtils.PPM)
        val b = world.injector.getRegistered(com.badlogic.gdx.physics.box2d.World::class.java).createBody(bd)
        b.userData = _e
        val pg = PolygonShape()
        pg.setAsBox(pc.w.toFloat() / 2f / PUtils.PPM, pc.h.toFloat() / 2f / PUtils.PPM)
        val fd = FixtureDef()
        fd.shape = pg
        b.createFixture(fd)
        pg.dispose()
        c.b = b

        setCentricPos(pc.x + pc.w / 2f, pc.y + pc.h / 2f, _e)


    }

    fun saveMOSyncPos(x: Float, y: Float, _e: Int) {

        setSyncCentricPos(x, y, _e)

        if (!world.getMapper(MObjectC::class.java).has(_e)) return

        val c = world.getMapper(MObjectC::class.java).get(_e)
        c.x = x.toInt() - c.w / 2
        c.y = y.toInt() - c.h / 2

    }

    fun syncPos(_e: Int) {

        setSyncCentricPos(getPhysPos(_e)!!.element0, getPhysPos(_e)!!.element1, _e)

    }
}
