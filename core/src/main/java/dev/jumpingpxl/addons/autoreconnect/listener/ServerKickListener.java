package dev.jumpingpxl.addons.autoreconnect.listener;

import dev.jumpingpxl.addons.autoreconnect.AutoReconnect;
import dev.jumpingpxl.addons.autoreconnect.AutoReconnectConfiguration;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.event.client.network.server.ServerKickEvent.Context;

public class ServerKickListener {

  private final AutoReconnect autoReconnect;

  public ServerKickListener(AutoReconnect autoReconnect) {
    this.autoReconnect = autoReconnect;
  }

  @Subscribe
  public void onServerKick(ServerKickEvent event) {
    Context context = event.context();
    AutoReconnectConfiguration configuration = this.autoReconnect.configuration();
    boolean autoReconnect = configuration.autoReconnect().get();
    if (context == Context.PLAY) {
      if (!configuration.reconnectOnKick().get()) {
        autoReconnect = false;
      }
    } else if (!configuration.reconnectOnLoginDeny().get()) {
      autoReconnect = false;
    }

    this.autoReconnect.updateServerData(event.serverData(), autoReconnect);
  }
}
