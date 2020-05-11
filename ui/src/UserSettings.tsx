import React from "react";
import axios from "axios";
import "./UserSettings.css";
import { useCookies } from "react-cookie";
import * as Yup from "yup";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";
import { useFormik } from "formik";
import { useHistory } from "react-router-dom";
import NavBar from "./NavBar";

const validationSchema = Yup.object({
  username: Yup.string().when("password", {
    is: (value) => !value || value.length === 0,
    then: Yup.string().email().max(30).required("Required"),
  }),
  password: Yup.string()
    .min(8)
    .max(50)
    .matches(
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/,
      "Password must have 8 characters, 1 lowercase, 1 uppercase, 1 number, and 1 special character"
    ),
  confirmPassword: Yup.string().when("password", {
    is: (value) => value && value.length > 0,
    then: Yup.string()
      .oneOf([Yup.ref("password"), null], "Passwords must match")
      .required("Required"),
  }),
});

function UserSettings(props: any) {
  const [cookies, setCookie] = useCookies(["userToken", "userId", "username"]);
  const history = useHistory();

  const { handleSubmit, handleChange, values, errors } = useFormik({
    initialValues: {
      username: "",
      password: "",
      confirmPassword: "",
    },
    validationSchema,
    onSubmit(values) {
      const username = values.username;
      const password = values.password;
      values.username = "";
      values.password = "";
      values.confirmPassword = "";
      axios
        .put(
          "http://localhost:8080/users/settings",
          {
            id: cookies.userId,
            username: username,
            password: password,
          },
          { headers: { Authorization: cookies.userToken } }
        )
        .then((res) => {
          if (res.data === "User has been updated") {
            if (username.length > 0) {
              setCookie("username", username, { path: "/" });
            }
            history.push(`/user/settings`);
            alert("User has been updated");
          } else {
            alert("Error updating settings");
          }
        });
    },
  });

  return (
    <div className="settings">
      <NavBar></NavBar>
      <header className="settingsHeader">
        <Button href="/create-meme" variant="primary">
          Home
        </Button>
        <h1>View and Edit your Settings</h1>
        <Form onSubmit={handleSubmit}>
          <InputGroup>
            <InputGroup.Prepend>
              <InputGroup.Text>Username</InputGroup.Text>
            </InputGroup.Prepend>
            <FormControl
              type="text"
              name="username"
              placeholder={cookies.username}
              onChange={handleChange}
              value={values.username}
            />
          </InputGroup>
          <p style={{ color: "red" }}>
            {errors.username ? errors.username : null}
          </p>
          <br></br>
          <InputGroup>
            <InputGroup.Prepend>
              <InputGroup.Text>Change Password</InputGroup.Text>
            </InputGroup.Prepend>
            <FormControl
              type="password"
              name="password"
              placeholder="Password..."
              onChange={handleChange}
              value={values.password}
            />
          </InputGroup>
          <p style={{ color: "red" }}>
            {errors.password ? errors.password : null}
          </p>
          <br></br>
          <InputGroup>
            <InputGroup.Prepend>
              <InputGroup.Text>Confirm Change Password</InputGroup.Text>
            </InputGroup.Prepend>
            <FormControl
              type="password"
              name="confirmPassword"
              placeholder="Confirm Password..."
              onChange={handleChange}
              value={values.confirmPassword}
            />
          </InputGroup>
          <p style={{ color: "red" }}>
            {errors.confirmPassword ? errors.confirmPassword : null}
          </p>
          <Button type="submit" variant="primary">
            Update Settings
          </Button>
        </Form>
      </header>
    </div>
  );
}

export default UserSettings;
