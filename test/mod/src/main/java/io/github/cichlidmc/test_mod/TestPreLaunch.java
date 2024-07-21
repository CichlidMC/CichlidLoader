package io.github.cichlidmc.test_mod;

import io.github.cichlidmc.cichlid.api.Mod;
import io.github.cichlidmc.cichlid.api.mod.entrypoint.PreLaunchEntrypoint;

public class TestPreLaunch implements PreLaunchEntrypoint {
	@Override
	public void preLaunch(Mod mod) {
		System.out.println("Test mod pre-launch!!!!");
	}
}
