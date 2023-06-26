package com.tajohnsonprod.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
  final Drop game;

  OrthographicCamera camera;

  public MainMenuScreen(final Drop game) {
    this.game = game;

    this.camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0.2f, 1);

    camera.update();
    game.spriteBatch.setProjectionMatrix(camera.combined);

    game.spriteBatch.begin();
    game.bitmapFont.draw(game.spriteBatch, "Welcome to Drop!", 100, 150);
    game.bitmapFont.draw(game.spriteBatch, "Tap anywhere to begin.", 100, 100);
    game.spriteBatch.end();

    if (Gdx.input.isTouched()) {
      game.setScreen((new GameScreen(game)));
      dispose();
    }
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