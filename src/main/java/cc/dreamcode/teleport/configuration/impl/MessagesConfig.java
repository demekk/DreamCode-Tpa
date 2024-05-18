package cc.dreamcode.teleport.configuration.impl;

import cc.dreamcode.teleport.type.TeleportMessageType;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Header("messages config")
@Getter
public class MessagesConfig extends OkaeriConfig {

    public final String teleportationWasBeSuccessful = "&aTeleportacja przebiegła pomyślnie!";
    public final String cancelledTeleportation = "&aTeleportacja została anulowana!";

    @Comment("Allow types: CHAT, ACTIONBAR")
    public final TeleportMessageType teleportMessageType = TeleportMessageType.ACTIONBAR;

    public final String teleportationInProgress = "&fTrwa teleportacja pozostało &9{TIME} &7sekund!";

    public final String receiverIsNotExists = "&cOsoba do której próbujesz się przeteleportować nie istnieje!";
    public final String yourRequestAlreadyExists = "&cNie możesz wysłać ponownie prośby o teleportacje ponieważ już ją wysłałeś!";

    public final String successfulSentInvite = "&aPomyślnie wysłano prośbę o teleportacje do gracza &2{RECEIVER}&a!";

    public final List<String> receiverHasRequest = Arrays.asList(
            "&aOtrzymano prośbę o teleportacje od gracza &2{REQUESTER}&a!",
            "",
            " &8» &7Wpisz &f/tpaccept <nick> &8- &7aby, zaakceptować prośbę!",
            " &8» &7Wpisz &f/tpdeny <nick> &8- &7aby, odrzucić prośbę!",
            "",
            "&cPosiadasz &4120 &csekund na zaakceptowanie prośby!"
    );

    public final String successfulAcceptedInvite = "&aPomyślnie zaakceptowano prośbę o teleportacje gracza &2{REQUESTER}&a!";
    public final String successfulDeclinedInvite = "&aPomyślnie odrzucono prośbę o teleportacje gracza &2{REQUESTER}&a!";

    public final String yourRequestHasDecline = "&cTwoja prośba o teleportacje do gracza &4{RECEIVER} &czostała odrzucona!";

    public final String haveNotRequest = "&cNie posiadasz prośby o teleportacje od tego gracza";
    public final String haveNotAnyRequest = "&cNie posiadasz żadnej prośby o teleportacje!";


}
