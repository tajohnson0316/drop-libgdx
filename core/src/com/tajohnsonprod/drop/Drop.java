package com.tajohnsonprod.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Drop extends ApplicationAdapter {
  private Texture dropImage;
  private Texture bucketImage;
  private Sound dropSound;
  private Music rainMusic;

  private OrthographicCamera camera;
  private SpriteBatch batch;

  private Rectangle bucket;

  private Array<Rectangle> raindrops;
  private Long lastDropTime;

  @Override
  public void create() {
    // load the images for the droplet and the bucket, 64x64 pixels each
    dropImage = new Texture(Gdx.files.internal("drop.png"));
    bucketImage = new Texture(Gdx.files.internal("bucket.png"));

    // load the drop sound effect and the rain background "music"
    dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

    // start the playback of the background music immediately
    rainMusic.setLooping(true);
    rainMusic.play();

    // create camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    // create the sprite batch
    batch = new SpriteBatch();

    // create the bucket
    bucket = new Rectangle();
    bucket.x = ((float) 800 / 2) - ((float) 64 / 2);
    bucket.y = 20;
    bucket.width = 64;
    bucket.height = 64;

    // create the raindrops
    raindrops = new Array<Rectangle>();
    spawnRaindrop();
  }

  @Override
  public void render() {
    ScreenUtils.clear(0, 0, 0.2f, 1);

    camera.update();

    batch.setProjectionMatrix(camera.combined);

    // Begin the sprite batch here
    batch.begin();

    batch.draw(bucketImage, bucket.x, bucket.y);
    for (Rectangle raindrop : raindrops) {
      batch.draw(dropImage, raindrop.x, raindrop.y);
    }

    batch.end();

    // move the bucket based on touch/mouse click position
    if (Gdx.input.isTouched()) {
      Vector3 touchPos = new Vector3();
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);
      bucket.x = touchPos.x - ((float) 64 / 2);
    }

    // move the bucket 200 pixes per second in the direction of the button press
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
      bucket.x -= 200 * Gdx.graphics.getDeltaTime();
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
      bucket.x += 200 * Gdx.graphics.getDeltaTime();

    // keep the bucket in bounds
    if (bucket.x < 0)
      bucket.x = 0;
    if (bucket.x > 800 - 64)
      bucket.x = 800 - 64;

    // spawn raindrops when necessary
    if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
      spawnRaindrop();

    // move raindrops at 200 pixels per second down the viewport
    for (Iterator<Rectangle> iterator = raindrops.iterator(); iterator.hasNext(); ) {
      Rectangle raindrop = iterator.next();
      raindrop.y -= 200 * Gdx.graphics.getDeltaTime();

      // if a raindrop hits the bottom of the viewport
      if (raindrop.y + 64 < 0)
        iterator.remove();

      // if a raindrop lands in the bucket
      if (raindrop.overlaps(bucket)) {
        dropSound.play();
        iterator.remove();
      }
    }
  }

  @Override
  public void dispose() {
    dropImage.dispose();
    bucketImage.dispose();
    dropSound.dispose();
    rainMusic.dispose();
    batch.dispose();
  }

  private void spawnRaindrop() {
    Rectangle raindrop = new Rectangle();

    raindrop.x = MathUtils.random(0, 800 - 64);
    raindrop.y = 480;
    raindrop.width = 64;
    raindrop.height = 64;
    raindrops.add(raindrop);
    lastDropTime = TimeUtils.nanoTime();
  }
}