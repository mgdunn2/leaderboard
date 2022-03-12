package com.bugval;

import com.bugval.domain.Score;
import com.bugval.infra.GsonModule;
import com.bugval.infra.ServicesModule;
import com.bugval.operations.IterationOperation;
import com.bugval.util.RequestScope;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LeaderBoard {
  public static void main(String[] args) {
    LeaderBoard leaderBoard = DaggerLeaderBoard_LeaderBoardComponent.create().leaderBoard();

    Executors.newScheduledThreadPool(1)
        .scheduleAtFixedRate(
            () -> {
              try {
                leaderBoard.requestProvider.get().build().iterationOperation().handleIteration();
              } catch (Exception e) {
                e.printStackTrace();
              }
            },
            1_000,
            5_000,
            TimeUnit.MILLISECONDS);
    CountDownLatch latch = new CountDownLatch(1);
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private final Provider<Request.Builder> requestProvider;
  private Map<Integer, Score> positionToScore;
  private Map<String, Score> playerToScore;

  @Inject
  public LeaderBoard(Provider<Request.Builder> requestProvider) {
    this.requestProvider = requestProvider;
  }

  @Singleton
  @Component(modules = {GsonModule.class, Request.RequestModule.class})
  public interface LeaderBoardComponent {
    LeaderBoard leaderBoard();
  }

  @RequestScope
  @Subcomponent(
      modules = {
        ServicesModule.class,
      })
  public interface Request {
    IterationOperation iterationOperation();

    @Subcomponent.Builder
    interface Builder {
      Request build();
    }

    @Module(subcomponents = Request.class)
    interface RequestModule {}
  }
}
