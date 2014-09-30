package com.example.yinxiang;

import android.app.Activity;
import android.os.Bundle;

import com.evernote.client.android.ClientFactory;
import com.evernote.client.android.EvernoteSession;
import com.evernote.client.oauth.EvernoteAuthToken;
import com.evernote.edam.notestore.NoteStore;
import com.evernote.edam.type.Notebook;

import org.scribe.model.Token;

import java.util.List;

/**
 * Created by kuangcheng on 2014/9/29.
 */
public class YinXiangMain extends Activity {

    public static final String DEVELOP_TOKEN = "S=s15:U=220442:E=150194997e3:C=148c1986880:P=1cd:A=en-devtoken:V=2:H=79cb54e3c8cf32a63b1d05b524039dae";

    public static final String NOTE_STORE_URL = "https://app.yinxiang.com/shard/s15/notestore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String developerToken = "my developer token";

//// Set up the NoteStore client
        Token toekn = new Token(DEVELOP_TOKEN, NOTE_STORE_URL);
        EvernoteAuthToken evernoteAuth = new EvernoteAuthToken(toekn, true);
        EvernoteSession.getOpenSession().persistAuthenticationToken(
                getApplicationContext(), authToken, mSelectedBootstrapProfile.getSettings().getServiceHost())

        ClientFactory factory = new ClientFactory(evernoteAuth);
//        NoteStoreClient noteStore = factory.createNoteStoreClient();
//
//// Make API calls, passing the developer token as the authenticationToken param
//        List<Notebook> notebooks = noteStore.listNotebooks();
//
//        for (Notebook notebook : notebooks) {
//            System.out.println("Notebook: " + notebook.getName());
//        }

    }
}
