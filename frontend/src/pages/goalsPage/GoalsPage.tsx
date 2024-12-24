import React, {useCallback, useContext, useEffect, useState} from "react";
import Page from "../../components/page/page";
import styles from "./goalsPage.module.css"
import {useNavigate} from "react-router-dom";
import {AppContext} from "../../context/context";
import PrimaryButton from "../../components/primaryButton/primaryButton";
import {MdDelete} from "react-icons/md";

function GoalsPage() {

    const context = useContext(AppContext);

    return (
        <Page title={"Cele"} showUserSwitch={true}>

        </Page>
    );

}

export default GoalsPage;
