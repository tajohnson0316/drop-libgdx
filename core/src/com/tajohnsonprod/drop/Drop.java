package com.tajohnsonprod.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {
  public SpriteBatch spriteBatch;
  public BitmapFont bitmapFont;

  public void create() {
    spriteBatch = new SpriteBatch();
    bitmapFont = new BitmapFont();
    this.setScreen(new MainMenuScreen(this));
  }

  public void render() {
    super.render();
  }

  public void dispose() {
    spriteBatch.dispose();
    bitmapFont.dispose();
  }
}