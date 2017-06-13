package com.junwu.user.ui.impui;


import com.jaryjun.common_base.component.dialog.DialogExchangeModel;

/**
 * Created by youj on 2015/7/2.
 * IHomeActivityUI
 */
public interface IHomeActivityUI {

  void showNewVersionDialog(DialogExchangeModel dialogExchangeModel);

   void processVersionUpdate(String newVersion, String newVersionUrl, boolean isForce);

   void exitUpdateDialog(boolean isForceUpdate);
}

