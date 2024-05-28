import React, { useState, useEffect } from 'react';
import { Dropdown } from 'primereact/dropdown';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import { InputTextarea } from 'primereact/inputtextarea';
import { Tooltip } from 'primereact/tooltip';
import { FaInfoCircle } from 'react-icons/fa';
import 'primereact/resources/themes/saga-blue/theme.css'; // Theme CSS
import 'primereact/resources/primereact.min.css'; // Core CSS
import 'primeicons/primeicons.css';
import axios from 'axios';
import './NonProfits.css'; // Import your custom CSS file

const NonProfits = ({ foundations, nonProfits }) => {
  const [selectedFoundation, setSelectedFoundation] = useState(null);
  const [selectedNonProfits, setSelectedNonProfits] = useState([]);
  const [message, setMessage] = useState('');
  const [displayDialog, setDisplayDialog] = useState(false);

  const sendEmailButtonEnabled = selectedNonProfits.length > 0 && selectedFoundation?.id > 0;

  const handleSendEmail = () => {
    // Make API call to create email data
    const data = {
      message: message,
      foundation: { id: selectedFoundation.id },
      nonProfits: selectedNonProfits.map(nonProfit => ({ id: nonProfit.id }))
    };

    axios.post('http://localhost:8080/api/emaildata', data)
      .then(response => {
        console.log('Email sent successfully:', response.data);
        setDisplayDialog(false);
        // Reset state
        setSelectedNonProfits([]);
        setMessage('');
      })
      .catch(error => {
        console.error('Error sending email:', error);
      });
  };

  return (
    <div className="p-grid non-profits-container">
      <div className="p-col">
        <div className="dropdown-container">
          <Dropdown
            value={selectedFoundation}
            options={foundations.map(f => ({ name: f.email, id: f.id }))}
            onChange={(e) => setSelectedFoundation(e.value)}
            optionLabel="name"
            placeholder="Select Foundation"
          />
          <FaInfoCircle id="info-icon" className="info-icon" data-pr-tooltip="Select a foundation and non-profits to send an email" />
          <Tooltip target="#info-icon" />
        </div>
        <DataTable value={nonProfits} selectionMode="multiple" selection={selectedNonProfits} onSelectionChange={(e) => setSelectedNonProfits(e.value)}>
          <Column selectionMode="multiple" style={{ width: '3em' }} />
          <Column field="id" header="ID" />
          <Column field="name" header="Name" />
          <Column field="address" header="Address" />
          <Column field="email" header="Email" />
        </DataTable>
        <Button label="Send Email" className="p-button-primary send-email-button" disabled={!sendEmailButtonEnabled} onClick={() => setDisplayDialog(true)} />
      </div>
      <Dialog visible={displayDialog} onHide={() => setDisplayDialog(false)} header="Send Email" modal className="send-email-dialog">
        <div className="dialog-content">
          <label htmlFor="message">Message:</label>
          <InputTextarea id="message" rows="5" cols="30" value={message} onChange={(e) => setMessage(e.target.value)} className="message-textarea" />
        </div>
        <div className="dialog-buttons">
          <Button label="Cancel" className="p-button-text" onClick={() => setDisplayDialog(false)} />
          <Button label="Send" className="p-button-primary" onClick={handleSendEmail} />
        </div>
      </Dialog>
    </div>
  );
};

export default NonProfits;
